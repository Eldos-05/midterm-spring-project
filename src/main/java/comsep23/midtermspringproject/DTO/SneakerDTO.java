package comsep23.midtermspringproject.DTO;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder

public class SneakerDTO {

    private Long id;
    private String name;
    private String brand;
    private String model;
    private double price;

}
