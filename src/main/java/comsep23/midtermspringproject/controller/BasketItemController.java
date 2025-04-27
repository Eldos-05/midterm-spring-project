package comsep23.midtermspringproject.controller;

import comsep23.midtermspringproject.DTO.BasketItemDTO;
import comsep23.midtermspringproject.entity.BasketItem;
import comsep23.midtermspringproject.mappers.BasketItemMapper;
import comsep23.midtermspringproject.service.BasketItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/basket-items")
public class BasketItemController {

    private final BasketItemService basketItemService;
    private final BasketItemMapper basketItemMapper;

    @Autowired
    public BasketItemController(BasketItemService basketItemService, BasketItemMapper basketItemMapper) {
        this.basketItemService = basketItemService;
        this.basketItemMapper = basketItemMapper;
    }

    // Get all basket items
    @GetMapping
    public ResponseEntity<List<BasketItemDTO>> getAllBasketItems() {
        List<BasketItem> basketItems = basketItemService.getAllBasketItems();
        List<BasketItemDTO> basketItemDTOs = basketItemMapper.toBasketItemDTOList(basketItems);
        return ResponseEntity.ok(basketItemDTOs);
    }

    // Get a basket item by its ID
    @GetMapping("/{id}")
    public ResponseEntity<BasketItemDTO> getBasketItemById(@PathVariable Long id) {
        Optional<BasketItem> basketItem = basketItemService.getBasketItemById(Math.toIntExact(id));
        return basketItem.map(bi -> ResponseEntity.ok(basketItemMapper.toBasketItemDTO(bi)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new basket item
    @PostMapping
    public ResponseEntity<BasketItemDTO> createBasketItem(@Valid @RequestBody BasketItemDTO basketItemDTO) {
        BasketItem basketItem = basketItemMapper.toBasketItem(basketItemDTO);
        BasketItem createdBasketItem = basketItemService.createBasketItem(basketItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(basketItemMapper.toBasketItemDTO(createdBasketItem));
    }

    // Update an existing basket item
    @PutMapping("/{id}")
    public ResponseEntity<BasketItemDTO> updateBasketItem(@PathVariable Long id, @Valid @RequestBody BasketItemDTO basketItemDTO) {
        BasketItem basketItem = basketItemMapper.toBasketItem(basketItemDTO);
        basketItem.setId(id);
        BasketItem updatedBasketItem = basketItemService.updateBasketItem(basketItem);
        return updatedBasketItem != null
                ? ResponseEntity.ok(basketItemMapper.toBasketItemDTO(updatedBasketItem))
                : ResponseEntity.notFound().build();
    }

    // Delete a basket item by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasketItem(@PathVariable Long id) {
        basketItemService.deleteBasketItem(Math.toIntExact(id));
        return ResponseEntity.noContent().build();
    }
}
