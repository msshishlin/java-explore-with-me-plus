package ewm.hit;

import ewm.CreateEndpointHitDto;
import ewm.EndpointStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitService {
    void createEndpointHit(CreateEndpointHitDto createEndpointHitDto);

    List<EndpointStatDto> viewStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
