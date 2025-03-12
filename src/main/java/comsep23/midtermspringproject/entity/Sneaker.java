package comsep23.midtermspringproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private double price;

    @Override
    public String toString() {
        return "Sneaker{id=" + id + ", name='" + name + "', brand='" + brand + "', model='" + model + "', price=" + price + "}";
    }

    @OneToMany(mappedBy = "sneaker")
    private List<BasketItem> basketItems;
}
