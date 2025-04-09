package ewm.client;

import ewm.CreateEndpointHitDto;
import ewm.EndpointStatDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatsClient {
    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:9090").build();

    public Mono<ResponseEntity<Void>> sendHit(CreateEndpointHitDto createEndpointHitDto) {
        return webClient.post()
                .uri("/hit")
                .bodyValue(createEndpointHitDto)
                .retrieve()
                .toEntity(Void.class);
    }

    public Mono<ResponseEntity<List<EndpointStatDto>>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String strStart = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String strEnd = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", strStart)
                        .queryParam("end", strEnd)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .toEntityList(EndpointStatDto.class);
    }
}
