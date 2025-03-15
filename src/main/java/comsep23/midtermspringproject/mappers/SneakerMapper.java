package comsep23.midtermspringproject.mappers;

import comsep23.midtermspringproject.DTO.SneakerDTO;
import comsep23.midtermspringproject.entity.Sneaker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SneakerMapper {

    SneakerDTO toSneakerDTO(Sneaker sneaker);
    Sneaker toSneakerEntity(SneakerDTO sneakerDTO);
    List<SneakerDTO> toSneakerDTOList(List<Sneaker> sneakers);
}
