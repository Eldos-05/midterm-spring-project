package comsep23.midtermspringproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "sneaker")
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Brand cannot be blank")
    @Size(max = 30, message = "Brand must be less than 30 characters")
    @Column(nullable = false)
    private String brand;

    private String model;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "sneaker", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems;

    @Override
    public String toString() {
        return "Sneaker{id=" + id + ", name='" + name + "', brand='" + brand + "', model='" + model + "', price=" + price + "}";
    }
}
