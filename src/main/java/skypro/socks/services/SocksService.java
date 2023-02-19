package skypro.socks.services;

import skypro.socks.constants.Operation;
import skypro.socks.models.Socks;
import org.springframework.http.ResponseEntity;

public interface SocksService {
    ResponseEntity<String> incomeSocks(Socks socks);

    ResponseEntity<String> outcomeSocks(Socks socks);

    ResponseEntity<String> getQuantityOfSocks(String color, Integer cottonPart, Operation operation);

}
