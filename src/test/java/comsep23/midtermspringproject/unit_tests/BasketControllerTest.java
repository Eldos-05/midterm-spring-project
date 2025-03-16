package comsep23.midtermspringproject.unit_tests;
import comsep23.midtermspringproject.controller.BasketController;
import comsep23.midtermspringproject.DTO.BasketDTO;
import comsep23.midtermspringproject.entity.Basket;
import comsep23.midtermspringproject.mappers.BasketMapper;
import comsep23.midtermspringproject.service.BasketService;
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

@WebMvcTest(BasketController.class)
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @MockBean
    private BasketMapper basketMapper;

    @Test
    public void testGetAllBaskets() throws Exception {
        List<Basket> baskets = Arrays.asList(new Basket(), new Basket());
        when(basketService.getAllBaskets()).thenReturn(baskets);
        when(basketMapper.toBasketDTOList(baskets)).thenReturn(Arrays.asList(new BasketDTO(), new BasketDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/baskets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}