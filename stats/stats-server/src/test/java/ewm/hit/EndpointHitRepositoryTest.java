package ewm.hit;

import ewm.EndpointStatDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EndpointHitRepositoryTest {
    private final EndpointHitRepository endpointHitRepository;

    @Test
    @DisplayName("Статистика по запросам без списка uri")
    public void shouldGetStats() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 2, 23, 59, 59);
        List<EndpointStatDto> stats = List.of(
                new EndpointStatDto("/test", "/test/test", 3L),
                new EndpointStatDto("/test2", "/test2/test", 1L));

        List<EndpointStatDto> statsUnique = List.of(
                new EndpointStatDto("/test", "/test/test", 2L),
                new EndpointStatDto("/test2", "/test2/test", 1L));

        Optional<List<EndpointStatDto>> statsOptional = Optional.of(
                endpointHitRepository.getStats(start, end, false));

        assertThat(statsOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(stats);

        statsOptional = Optional.of(
                endpointHitRepository.getStats(start, end, true));

        assertThat(statsOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(statsUnique);
    }

    @Test
    @DisplayName("Статистика по запросам со списком uri")
    public void shouldGetStatsWithUris() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 2, 23, 59, 59);

        List<String> uris = List.of("/test/test".toUpperCase());

        List<EndpointStatDto> statsWithUris = List.of(
                new EndpointStatDto("/test", "/test/test", 3L));

        List<EndpointStatDto> statsWithUrisUnique = List.of(
                new EndpointStatDto("/test", "/test/test", 2L));

        Optional<List<EndpointStatDto>> statsOptional = Optional.of(
                endpointHitRepository.getStatsWithUris(start, end, false, uris));

        assertThat(statsOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(statsWithUris);

        statsOptional = Optional.of(
                endpointHitRepository.getStatsWithUris(start, end, true, uris));

        assertThat(statsOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(statsWithUrisUnique);
    }
}
