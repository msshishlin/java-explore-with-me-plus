package ewm.compilations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class NewCompilationDto {
    @NotNull
    private Set<Long> events;

    private Boolean pinned;

    @Length(max = 50, message = "Наименование подборки не может быть больше 50")
    @Length(min = 1, message = "Наименование подборки не может быть меньше 1")
    @NotBlank
    private String title;
}
