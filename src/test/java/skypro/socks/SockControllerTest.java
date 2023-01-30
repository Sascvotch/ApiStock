package skypro.socks;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import skypro.socks.controllers.SocksController;
import skypro.socks.models.Socks;
import skypro.socks.repositories.SocksRepositories;
import skypro.socks.services.SocksService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static skypro.socks.constants.ConstantsForTest.*;


@WebMvcTest(SocksController.class)
public class SockControllerTest {
    @MockBean
    private SocksRepositories socksRepositories;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SocksService socksService;
    @InjectMocks
    private SocksController socksController;

    @BeforeEach
    void setUp() throws JSONException {
        SOCKS.setId(1L);
        SOCKS.setQuantity(25);
        SOCKS.setColor("red");
        SOCKS.setCottonPart(50);

        SOCKSOBJECT.put("color", SOCKS.getColor());
        SOCKSOBJECT.put("cottonPart", SOCKS.getCottonPart().toString());
        SOCKSOBJECT.put("quantity", SOCKS.getQuantity().toString());
    }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(socksController).isNotNull();
    }

    @Test
    public void incomeSocksTest() throws Exception {
        when(socksRepositories.save(any(Socks.class))).thenReturn(SOCKS);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.of(SOCKS));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(SOCKSOBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void outcomeSocksTestWhenSocksAlredyExsist() throws Exception {
        when(socksRepositories.save(any(Socks.class))).thenReturn(SOCKS);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.of(SOCKS));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(SOCKSOBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void outcomeSocksTestWhenSuchSocksNotExsist() throws Exception {
        when(socksRepositories.save(any(Socks.class))).thenReturn(SOCKS);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(SOCKSOBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
