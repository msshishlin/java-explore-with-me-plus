package ewm.compilations;

import ewm.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder(toBuilder = true)
@Data
public class CompilationDto {

    private Long id;

    private String title;

    private boolean pinned;

    private Set<Event> events;
}
