package comsep23.midtermspringproject.controller;

import comsep23.midtermspringproject.DTO.SneakerDTO;
import comsep23.midtermspringproject.entity.Sneaker;
import comsep23.midtermspringproject.mappers.SneakerMapper;
import comsep23.midtermspringproject.service.SneakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sneakers")
public class SneakerController {

    private final SneakerService sneakerService;
    private final SneakerMapper sneakerMapper;

    @Autowired
    public SneakerController(SneakerService sneakerService, SneakerMapper sneakerMapper) {
        this.sneakerService = sneakerService;
        this.sneakerMapper = sneakerMapper;
    }

    @GetMapping
    public ResponseEntity<List<SneakerDTO>> getAllSneakers() {
        List<Sneaker> sneakers = sneakerService.getAllSneakers();
        List<SneakerDTO> sneakerDTOs = sneakerMapper.toSneakerDTOList(sneakers);
        return ResponseEntity.ok(sneakerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SneakerDTO> getSneakerById(@PathVariable Long id) {
        Optional<Sneaker> sneaker = sneakerService.getSneakerById(id);
        return sneaker.map(s -> ResponseEntity.ok(sneakerMapper.toSneakerDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SneakerDTO> createSneaker(@RequestBody SneakerDTO sneakerDTO) {
        Sneaker sneaker = sneakerMapper.toSneaker(sneakerDTO);
        Sneaker createdSneaker = sneakerService.createSneaker(sneaker);
        return ResponseEntity.status(HttpStatus.CREATED).body(sneakerMapper.toSneakerDTO(createdSneaker));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SneakerDTO> updateSneaker(@PathVariable Long id, @RequestBody SneakerDTO sneakerDTO) {
        Sneaker sneaker = sneakerMapper.toSneaker(sneakerDTO);
        sneaker.setId(id);
        Sneaker updatedSneaker = sneakerService.updateSneaker(sneaker);
        return updatedSneaker != null
                ? ResponseEntity.ok(sneakerMapper.toSneakerDTO(updatedSneaker))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSneaker(@PathVariable Long id) {
        sneakerService.deleteSneaker(id);
        return ResponseEntity.noContent().build();
    }
}
