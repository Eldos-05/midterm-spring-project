package comsep23.midtermspringproject.unit_tests;

import comsep23.midtermspringproject.DTO.SneakerDTO;
import comsep23.midtermspringproject.controller.SneakerController;
import comsep23.midtermspringproject.entity.Sneaker;
import comsep23.midtermspringproject.mappers.SneakerMapper;
import comsep23.midtermspringproject.service.SneakerService;
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

public class SneakerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SneakerService sneakerService;

    @Mock
    private SneakerMapper sneakerMapper;

    @InjectMocks
    private SneakerController sneakerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sneakerController).build();
    }

    @Test
    public void testFindById_existingSneaker() throws Exception {
        Sneaker sneaker = new Sneaker();
        SneakerDTO sneakerDTO = new SneakerDTO();
        when(sneakerService.getSneakerById(1L)).thenReturn(Optional.of(sneaker));
        when(sneakerMapper.toSneakerDTO(sneaker)).thenReturn(sneakerDTO);
        mockMvc.perform(get("/api/sneakers/1")).andExpect(status().isOk());
    }

    @Test
    public void testFindById_nonExistingSneaker() throws Exception {
        when(sneakerService.getSneakerById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/sneakers/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSneaker() throws Exception {
        SneakerDTO sneakerDTO = new SneakerDTO(); // Replace with your DTO
        Sneaker sneaker = new Sneaker(); // Replace with your Entity
        when(sneakerMapper.toSneaker(sneakerDTO)).thenReturn(sneaker);
        when(sneakerService.createSneaker(sneaker)).thenReturn(sneaker);
        mockMvc.perform(post("/api/sneakers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(sneakerDTO))).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSneaker_existingSneaker() throws Exception {
        SneakerDTO sneakerDTO = new SneakerDTO();
        Sneaker sneaker = new Sneaker();
        when(sneakerMapper.toSneaker(sneakerDTO)).thenReturn(sneaker);
        when(sneakerService.updateSneaker(sneaker)).thenReturn(sneaker);
        mockMvc.perform(put("/api/sneakers/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(sneakerDTO))).andExpect(status().isOk());
    }

    @Test
    public void testUpdateSneaker_nonExistingSneaker() throws Exception {
        SneakerDTO sneakerDTO = new SneakerDTO();
        Sneaker sneaker = new Sneaker();
        when(sneakerMapper.toSneaker(sneakerDTO)).thenReturn(sneaker);
        when(sneakerService.updateSneaker(sneaker)).thenReturn(null);
        mockMvc.perform(put("/api/sneakers/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(sneakerDTO))).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteSneaker() throws Exception {
        mockMvc.perform(delete("/api/sneakers/1")).andExpect(status().isNoContent());
        verify(sneakerService, times(1)).deleteSneaker(1L);
    }
}