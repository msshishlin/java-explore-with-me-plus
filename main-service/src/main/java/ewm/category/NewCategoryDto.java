package ewm.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotBlank(message = "Наименование категории не может быть пустым")
    @Length(max = 50, message = "Наименование категории не может быть больше 50 символов")
    private String name;
}
