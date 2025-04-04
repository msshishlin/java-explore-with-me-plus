package ewm.hit;

import ewm.CreateEndpointHitDto;
import ewm.EndpointHitDto;
import ewm.EndpointStatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    @Transactional
    public EndpointHitDto createEndpointHit(CreateEndpointHitDto createEndpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.INSTANCE.toEndpointHit(createEndpointHitDto);
        return EndpointHitMapper.INSTANCE.toEndpointHitDto(endpointHitRepository.save(endpointHit));
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
