package ewm.hit;

import ewm.CreateEndpointHitDto;
import ewm.EndpointStatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    public void createEndpointHit(CreateEndpointHitDto createEndpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.INSTANCE.toEndpointHit(createEndpointHitDto);
        endpointHitRepository.save(endpointHit);
    }

    @Override
    public List<EndpointStatDto> viewStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (uris == null || uris.isEmpty()) {
            return endpointHitRepository.getStats(start, end, unique);
        } else {
            return endpointHitRepository.getStatsWithUris(
                    start,
                    end,
                    unique,
                    uris.stream()
                            .map(String::toUpperCase)
                            .toList());
        }
    }
}
