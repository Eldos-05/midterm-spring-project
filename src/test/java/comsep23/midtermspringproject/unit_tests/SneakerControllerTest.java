package comsep23.midtermspringproject.unit_tests;


import comsep23.midtermspringproject.controller.SneakerController;
import comsep23.midtermspringproject.DTO.SneakerDTO;
import comsep23.midtermspringproject.entity.Sneaker;
import comsep23.midtermspringproject.mappers.SneakerMapper;
import comsep23.midtermspringproject.service.SneakerService;
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

@WebMvcTest(SneakerController.class)
public class SneakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SneakerService sneakerService;

    @MockBean
    private SneakerMapper sneakerMapper;

    @Test
    public void testGetAllSneakers() throws Exception {
        List<Sneaker> sneakers = Arrays.asList(new Sneaker(), new Sneaker());
        when(sneakerService.getAllSneakers()).thenReturn(sneakers);
        when(sneakerMapper.toSneakerDTOList(sneakers)).thenReturn(Arrays.asList(new SneakerDTO(), new SneakerDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sneakers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}