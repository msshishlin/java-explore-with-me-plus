package ewm.hit;

import ewm.CreateEndpointHitDto;
import ewm.EndpointStatDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@RequestBody @Valid CreateEndpointHitDto createEndpointHitDto) {
        log.info("Вызван метод POST /hit с телом запроса {}", createEndpointHitDto);
        endpointHitService.createEndpointHit(createEndpointHitDto);
        log.info("Метод POST /hit успешно выполнен");
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<EndpointStatDto> viewStats(@RequestParam(value = "start") @Valid @NotNull String startText,
                                           @RequestParam(value = "end") @Valid @NotNull String endText,
                                           @RequestParam(value = "uris", required = false) List<String> uris,
                                           @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info(
                "Вызван метод GET /stats c параметрами start = {}, end = {}, uris = {}, unique = {}",
                startText,
                endText,
                uris,
                unique);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startText, format);
        LocalDateTime end = LocalDateTime.parse(endText, format);
        List<EndpointStatDto> endpointStatDtos = endpointHitService.viewStats(start, end, uris, unique);
        log.info("Метод POST /stats вернул ответ {}", endpointStatDtos);
        return endpointStatDtos;
    }
}
