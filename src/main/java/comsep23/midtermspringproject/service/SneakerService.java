package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.SneakerDTO;
import comsep23.midtermspringproject.entity.Sneaker;
import comsep23.midtermspringproject.mappers.SneakerMapper;
import comsep23.midtermspringproject.repository.SneakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service

public class SneakerService {
    private final SneakerMapper sneakerMapper;
    private final SneakerRepository sneakerRepository;

    @Autowired
    public SneakerService(SneakerRepository sneakerRepository, SneakerMapper sneakerMapper) {
        this.sneakerRepository = sneakerRepository;
        this.sneakerMapper = sneakerMapper;
    }
    public List<SneakerDTO> getSneakerDTOs(String brand) {
        List<Sneaker> sneakers;

        if (StringUtils.hasText(brand)) {
            sneakers = sneakerRepository.findByBrand(brand);
        } else {
            sneakers = sneakerRepository.findAll();
        }

        return sneakerMapper.toSneakerDTOList(sneakers);
    }


   public List<SneakerDTO>toSneakerDTOList(List<Sneaker> sneakers) {
        return sneakerMapper.toSneakerDTOList(sneakers);
   }

    public List<Sneaker> getAllSneakers() {
        return sneakerRepository.findAll();
    }

    public Optional<Sneaker> getSneakerById(Long id) {
        return sneakerRepository.findById(id);
    }

    public Sneaker createSneaker(Sneaker sneaker) {
        return sneakerRepository.save(sneaker);
    }

    public Sneaker updateSneaker(Sneaker sneaker) {
        return sneakerRepository.save(sneaker);
    }

    public void deleteSneaker(Long id) {
        if (sneakerRepository.existsById(id)) {
            sneakerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sneaker with id " + id + " not found.");
        }
    }
}
