package ewm.client;

import ewm.EndpointHitDto;
import ewm.EndpointStatDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class StatsClient {
    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:9090").build();

    public Mono<Void> sendHit(EndpointHitDto hitDto) {
        return webClient.post()
                .uri("/hit")
                .bodyValue(hitDto)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<List<EndpointStatDto>> getStats(String start, String end, List<String> uris, boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris",
                                Optional.ofNullable(uris)
                                        .filter(list -> list.isEmpty())
                                        .map(list -> String.join(",", list)))
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EndpointStatDto>>() {
                });
    }
}
