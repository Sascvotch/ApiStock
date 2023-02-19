package skypro.socks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import skypro.socks.repositories.SocksRepositories;
import skypro.socks.services.impl.SocksServiceImpl;

import java.util.Optional;

import static skypro.socks.constants.ConstantsForTest.OUTSOCKS;
import static skypro.socks.constants.ConstantsForTest.SOCKS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SockServiceImplTest {
    @Mock
    private SocksRepositories socksRepositories;
    @InjectMocks
    private SocksServiceImpl socksService;


    @BeforeEach
    void setUp() {
        SOCKS.setId(1L);
        SOCKS.setColor("red");
        SOCKS.setCottonPart(50);
        SOCKS.setQuantity(25);

        OUTSOCKS.setColor("red");
        OUTSOCKS.setCottonPart(50);
     }

    @Test
    public void outcomeSocksTestWhenOutQuantityLessThanCurrentQuantity() {
        OUTSOCKS.setQuantity(20);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.of(SOCKS));
        String str = "Носки отправлены со склада";
        assertEquals(socksService.outcomeSocks(OUTSOCKS), ResponseEntity.ok(str));
        verify(socksRepositories, times(1)).findSocksByColorAndCottonPart(SOCKS.getColor(), SOCKS.getCottonPart());
        SOCKS.setQuantity(5);
        verify(socksRepositories, times(1)).save(SOCKS);

    }

    @Test
    public void outcomeSocksTestWhenOutQuantityMoreThanCurrentQuantity() {
        OUTSOCKS.setQuantity(30);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.of(SOCKS));
        String str = "Носков на складе недостаточно";
        assertEquals(socksService.outcomeSocks(OUTSOCKS), ResponseEntity.ok(str));
        verify(socksRepositories, times(1)).findSocksByColorAndCottonPart(SOCKS.getColor(), SOCKS.getCottonPart());
        verify(socksRepositories, times(0)).save(OUTSOCKS);
    }

    @Test
    public void outcomeSocksTestWhenOutQuantityEqualsThanCurrentQuantity() {
        OUTSOCKS.setQuantity(25);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.of(SOCKS));
        String str = "Носки отправлены со склада";
        assertEquals(socksService.outcomeSocks(OUTSOCKS), ResponseEntity.ok(str));
        verify(socksRepositories, times(1)).findSocksByColorAndCottonPart(SOCKS.getColor(), SOCKS.getCottonPart());
        verify(socksRepositories, times(1)).deleteById(SOCKS.getId());
    }

    @Test
    public void incomeSocksTestWhenSuchSocksAlreadyExsist() {
        OUTSOCKS.setQuantity(20);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.of(SOCKS));
        String str = "Носки добавлены на склад";
        assertEquals(socksService.incomeSocks(OUTSOCKS), ResponseEntity.ok(str));
        verify(socksRepositories, times(1)).findSocksByColorAndCottonPart(SOCKS.getColor(), SOCKS.getCottonPart());
        SOCKS.setQuantity(45);
        verify(socksRepositories, times(1)).save(SOCKS);

    }

    @Test
    public void incomeSocksTestWhenSuchSocksNotExsist() {
        OUTSOCKS.setQuantity(20);
        when(socksRepositories.findSocksByColorAndCottonPart(any(String.class), any(Integer.class))).thenReturn(Optional.empty());
        String str = "Носки добавлены на склад";
        assertEquals(socksService.incomeSocks(OUTSOCKS), ResponseEntity.ok(str));
        verify(socksRepositories, times(1)).findSocksByColorAndCottonPart(SOCKS.getColor(), SOCKS.getCottonPart());
        verify(socksRepositories, times(1)).save(OUTSOCKS);
    }
}
