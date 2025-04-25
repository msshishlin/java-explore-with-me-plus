package ewm.compilations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class UpdateCompilationRequest {
    private String title;

    private Set<Long> events;

    private Boolean pinned;
}
