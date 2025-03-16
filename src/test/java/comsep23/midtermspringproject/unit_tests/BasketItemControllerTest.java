package comsep23.midtermspringproject.unit_tests;

import comsep23.midtermspringproject.controller.BasketItemController;
import comsep23.midtermspringproject.DTO.BasketItemDTO;
import comsep23.midtermspringproject.entity.BasketItem;
import comsep23.midtermspringproject.mappers.BasketItemMapper;
import comsep23.midtermspringproject.service.BasketItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BasketItemController.class)
public class BasketItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketItemService basketItemService;

    @MockBean
    private BasketItemMapper basketItemMapper;

    @Test
    public void testGetAllBasketItems() throws Exception {
        List<BasketItem> basketItems = Arrays.asList(new BasketItem(), new BasketItem());
        when(basketItemService.getAllBasketItems()).thenReturn(basketItems);
        when(basketItemMapper.toBasketItemDTOList(basketItems)).thenReturn(Arrays.asList(new BasketItemDTO(), new BasketItemDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/basket-items"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}
