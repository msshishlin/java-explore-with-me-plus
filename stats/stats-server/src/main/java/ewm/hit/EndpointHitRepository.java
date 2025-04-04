package ewm.hit;

import ewm.EndpointStatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("""
           select new ewm.EndpointStatDto(h.app, h.uri, case when ?3 = true then count(distinct h.ip) else count(h) end)
           from EndpointHit as h
           where h.timestamp between ?1 and ?2
           group by h.app, h.uri
           order by case when ?3 = true then count(distinct h.ip) else count(h) end desc
           """)
    List<EndpointStatDto> getStats(LocalDateTime start, LocalDateTime end, boolean unique);

    @Query("""
           select new ewm.EndpointStatDto(h.app, h.uri, case when ?3 = true then count(distinct h.ip) else count(h) end)
           from EndpointHit as h
           where h.timestamp between ?1 and ?2
           and upper(h.uri) in (?4)
           group by h.app, h.uri
           order by case when ?3 = true then count(distinct h.ip) else count(h) end desc
           """)
    List<EndpointStatDto> getStatsWithUris(LocalDateTime start, LocalDateTime end, boolean unique, List<String> uris);
}
