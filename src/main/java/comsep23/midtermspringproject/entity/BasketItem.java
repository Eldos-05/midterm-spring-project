package comsep23.midtermspringproject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "basket_items")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "sneaker_id", nullable = false)
    private Sneaker sneaker;

    private int quantity;
    private double totalPrice;
}
