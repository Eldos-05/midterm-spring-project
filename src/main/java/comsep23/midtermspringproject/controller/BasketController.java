package comsep23.midtermspringproject.controller;

import comsep23.midtermspringproject.DTO.BasketDTO;
import comsep23.midtermspringproject.entity.Basket;
import comsep23.midtermspringproject.mappers.BasketMapper;
import comsep23.midtermspringproject.service.BasketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;
    private final BasketMapper basketMapper;

    @Autowired
    public BasketController(BasketService basketService, BasketMapper basketMapper) {
        this.basketService = basketService;
        this.basketMapper = basketMapper;
    }

    @GetMapping
    public ResponseEntity<List<BasketDTO>> getAllBaskets() {
        List<Basket> baskets = basketService.getAllBaskets();
        List<BasketDTO> basketDTOs = basketMapper.toBasketDTOList(baskets);
        return ResponseEntity.ok(basketDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasketDTO> getBasketById(@PathVariable Long id) {
        Optional<Basket> basket = basketService.getBasketById(id);
        return basket.map(b -> ResponseEntity.ok(basketMapper.toBasketDTO(b)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BasketDTO> createBasket( @Valid @RequestBody BasketDTO basketDTO) {
        Basket basket = basketMapper.toBasket(basketDTO);
        Basket createdBasket = basketService.createBasket(basket);
        return ResponseEntity.status(HttpStatus.CREATED).body(basketMapper.toBasketDTO(createdBasket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasketDTO> updateBasket(@PathVariable Long id, @Valid @RequestBody BasketDTO basketDTO) {
        Basket basket = basketMapper.toBasket(basketDTO);
        basket.setId(id);
        Basket updatedBasket = basketService.updateBasket(basket);
        return updatedBasket != null
                ? ResponseEntity.ok(basketMapper.toBasketDTO(updatedBasket))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long id) {
        basketService.deleteBasket(id);
        return ResponseEntity.noContent().build();
    }
}