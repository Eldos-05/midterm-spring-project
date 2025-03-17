package comsep23.midtermspringproject.unit_tests;


import comsep23.midtermspringproject.DTO.BasketItemDTO;
import comsep23.midtermspringproject.controller.BasketItemController;
import comsep23.midtermspringproject.entity.BasketItem;
import comsep23.midtermspringproject.mappers.BasketItemMapper;
import comsep23.midtermspringproject.service.BasketItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasketItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BasketItemService basketItemService;

    @Mock
    private BasketItemMapper basketItemMapper;

    @InjectMocks
    private BasketItemController basketItemController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(basketItemController).build();
    }

    @Test
    public void testFindById_existingBasketItem() throws Exception {
        BasketItem basketItem = new BasketItem();
        BasketItemDTO basketItemDTO = new BasketItemDTO();
        when(basketItemService.getBasketItemById(1)).thenReturn(Optional.of(basketItem));
        when(basketItemMapper.toBasketItemDTO(basketItem)).thenReturn(basketItemDTO);
        mockMvc.perform(get("/api/basket-items/1")).andExpect(status().isOk());
    }

    @Test
    public void testFindById_nonExistingBasketItem() throws Exception {
        when(basketItemService.getBasketItemById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/basket-items/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBasketItem() throws Exception {
        BasketItemDTO basketItemDTO = new BasketItemDTO();
        BasketItem basketItem = new BasketItem();
        when(basketItemMapper.toBasketItem(basketItemDTO)).thenReturn(basketItem);
        when(basketItemService.createBasketItem(basketItem)).thenReturn(basketItem);
        mockMvc.perform(post("/api/basket-items").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basketItemDTO))).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateBasketItem_existingBasketItem() throws Exception {
        BasketItemDTO basketItemDTO = new BasketItemDTO();
        BasketItem basketItem = new BasketItem();
        when(basketItemMapper.toBasketItem(basketItemDTO)).thenReturn(basketItem);
        when(basketItemService.updateBasketItem(basketItem)).thenReturn(basketItem);
        mockMvc.perform(put("/api/basket-items/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basketItemDTO))).andExpect(status().isOk());
    }

    @Test
    public void testUpdateBasketItem_nonExistingBasketItem() throws Exception {
        BasketItemDTO basketItemDTO = new BasketItemDTO();
        BasketItem basketItem = new BasketItem();
        when(basketItemMapper.toBasketItem(basketItemDTO)).thenReturn(basketItem);
        when(basketItemService.updateBasketItem(basketItem)).thenReturn(null);
        mockMvc.perform(put("/api/basket-items/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basketItemDTO))).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBasketItem() throws Exception {
        mockMvc.perform(delete("/api/basket-items/1")).andExpect(status().isNoContent());
        verify(basketItemService, times(1)).deleteBasketItem(1);
    }
}