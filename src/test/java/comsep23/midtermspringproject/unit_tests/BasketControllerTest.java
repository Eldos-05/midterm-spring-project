package comsep23.midtermspringproject.unit_tests;

import comsep23.midtermspringproject.DTO.BasketDTO;
import comsep23.midtermspringproject.controller.BasketController;
import comsep23.midtermspringproject.entity.Basket;
import comsep23.midtermspringproject.mappers.BasketMapper;
import comsep23.midtermspringproject.service.BasketService;
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

public class BasketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BasketService basketService;

    @Mock
    private BasketMapper basketMapper;

    @InjectMocks
    private BasketController basketController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(basketController).build();
    }

    @Test
    public void testFindById_existingBasket() throws Exception {
        Basket basket = new Basket();
        BasketDTO basketDTO = new BasketDTO();
        when(basketService.getBasketById(1L)).thenReturn(Optional.of(basket));
        when(basketMapper.toBasketDTO(basket)).thenReturn(basketDTO);
        mockMvc.perform(get("/api/baskets/1")).andExpect(status().isOk());
    }

    @Test
    public void testFindById_nonExistingBasket() throws Exception {
        when(basketService.getBasketById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/baskets/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBasket() throws Exception {
        BasketDTO basketDTO = new BasketDTO();
        Basket basket = new Basket();
        when(basketMapper.toBasket(basketDTO)).thenReturn(basket);
        when(basketService.createBasket(basket)).thenReturn(basket);
        mockMvc.perform(post("/api/baskets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basketDTO))).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateBasket_existingBasket() throws Exception {
        BasketDTO basketDTO = new BasketDTO();
        Basket basket = new Basket();
        when(basketMapper.toBasket(basketDTO)).thenReturn(basket);
        when(basketService.updateBasket(basket)).thenReturn(basket);
        mockMvc.perform(put("/api/baskets/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basketDTO))).andExpect(status().isOk());
    }

    @Test
    public void testUpdateBasket_nonExistingBasket() throws Exception {
        BasketDTO basketDTO = new BasketDTO();
        Basket basket = new Basket();
        when(basketMapper.toBasket(basketDTO)).thenReturn(basket);
        when(basketService.updateBasket(basket)).thenReturn(null);
        mockMvc.perform(put("/api/baskets/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basketDTO))).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBasket() throws Exception {
        mockMvc.perform(delete("/api/baskets/1")).andExpect(status().isNoContent());
        verify(basketService, times(1)).deleteBasket(1L);
    }
}