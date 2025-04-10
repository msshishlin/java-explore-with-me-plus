package ewm.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    @Length(max = 50, message = "Наименование категории не может быть больше 50 символов")
    private String name;
}
