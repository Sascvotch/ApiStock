package skypro.socks.services.impl;

import lombok.extern.slf4j.Slf4j;
import skypro.socks.constants.Operation;
import skypro.socks.models.Socks;
import skypro.socks.repositories.SocksRepositories;
import skypro.socks.services.SocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SocksServiceImpl implements SocksService {

    private final SocksRepositories socksRepositories;

    public SocksServiceImpl(SocksRepositories socksRepositories) {
        this.socksRepositories = socksRepositories;
    }

    @Override
    public ResponseEntity<String> incomeSocks(Socks incomeSocks) {
        log.info("incomeSocks" + incomeSocks.toString());
        Optional<Socks> socks = socksRepositories.findSocksByColorAndCottonPart(incomeSocks.getColor(), incomeSocks.getCottonPart());
        socks.ifPresentOrElse(e -> {
            e.setQuantity(e.getQuantity() + incomeSocks.getQuantity());
            socksRepositories.save(e);
        }, (() -> socksRepositories.save(incomeSocks)));
        return ResponseEntity.ok("Носки добавлены на склад");
    }

    @Override
    public ResponseEntity<String> outcomeSocks(Socks outcomeSocks) {
        log.info("outcomeSocks" + outcomeSocks.toString());
        Optional<Socks> socks = socksRepositories.findSocksByColorAndCottonPart(outcomeSocks.getColor(), outcomeSocks.getCottonPart());
        String str = "";
        if (socks.isPresent()) {
            int quantity = socks.get().getQuantity() - outcomeSocks.getQuantity();
            if (quantity < 0) {
                str = "Носков на складе недостаточно";
            }
            if (quantity == 0) {
                socksRepositories.deleteById(socks.get().getId());
                str = "Носки отправлены со склада";
            }
            if (quantity > 0) {
                socks.get().setQuantity(quantity);
                socksRepositories.save(socks.get());
                str = "Носки отправлены со склада";
            }
        } else {
            str = "Таких носков на складе нет";
        }
        return ResponseEntity.ok(str);
    }

    @Override
    public ResponseEntity<String> getQuantityOfSocks(String color, Integer cottonPart, Operation operation) {
        log.info("getQuantityOfSocks цвет:" + color + ", содержание хлопка:" + cottonPart + ", операция:" + operation);
        int quantity = 0;
        switch (operation) {
            case MORETHAN ->
                    quantity = socksRepositories.findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentCondition(color, cottonPart).orElse(0);
            case LESSTHAN ->
                    quantity = socksRepositories.findQuantityOfSocksByColorAndCottonPartLessThanCurrentCondition(color, cottonPart).orElse(0);
            case EQUALS ->
                    quantity = socksRepositories.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentCondition(color, cottonPart).orElse(0);

        }
        return ResponseEntity.ok("Найдено носков с цветом " + color + " и содержанием хлопка " + operation.getOperation() + " " + cottonPart + "%: " + quantity + " пар");
    }
}

