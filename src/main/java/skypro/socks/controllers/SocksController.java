package skypro.socks.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import skypro.socks.models.Socks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.socks.services.SocksService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/socks")
@Validated
public class SocksController {
    private final SocksService socksService;

    @Operation(summary = "приход носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Удалось добавить приход"),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "Произошла ошибка, независящая от вызывающей стороны")
            })
    @PostMapping("income")
    public ResponseEntity<String> incomeSocks(@Valid @RequestBody Socks socks) {
                return socksService.incomeSocks(socks);
    }

    @Operation(summary = "расход носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Удалось добавить расход"),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "Произошла ошибка, независящая от вызывающей стороны")
            })
    @PostMapping("outcome")
    public ResponseEntity<String> outcomeSocks(@Valid @RequestBody Socks socks) {
        return socksService.outcomeSocks(socks);
    }
    @Operation(summary = "общее количество носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запрос выполнен"),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "Произошла ошибка, независящая от вызывающей стороны")
            })
    @GetMapping()
    public ResponseEntity<String> getQuantityOfSocks(@Valid @RequestParam String color,@RequestParam Integer cottonPart, @RequestParam skypro.socks.constants.Operation operation) {
        return socksService.getQuantityOfSocks(color,cottonPart,operation);
    }

}
