package comsep23.midtermspringproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sneakers")
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    private String model;

    @Column(nullable = false)
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @Override
    public String toString() {
        return "Sneaker{id=" + id + ", name='" + name + "', brand='" + brand + "', model='" + model + "', price=" + price + "}";
    }
}
