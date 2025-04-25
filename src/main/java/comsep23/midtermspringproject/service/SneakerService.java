package comsep23.midtermspringproject.service;

import com.github.javafaker.Faker;
import comsep23.midtermspringproject.DTO.SneakerDTO;
import comsep23.midtermspringproject.entity.Sneaker;
import comsep23.midtermspringproject.mappers.SneakerMapper;
import comsep23.midtermspringproject.repository.SneakerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SneakerService {
    private final SneakerMapper sneakerMapper;
    private final SneakerRepository sneakerRepository;
    private List<Sneaker> sneakers;


    public List<SneakerDTO> getSneakerDTOs(String brand) {
        List<Sneaker> sneakersList;

        if (StringUtils.hasText(brand)) {
            sneakersList = sneakerRepository.findByBrand(brand);
        } else {
            sneakersList = sneakerRepository.findAll();
        }

        return sneakerMapper.toSneakerDTOList(sneakersList);
    }

    public List<SneakerDTO> toSneakerDTOList(List<Sneaker> sneakersList) {
        return sneakerMapper.toSneakerDTOList(sneakersList);
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