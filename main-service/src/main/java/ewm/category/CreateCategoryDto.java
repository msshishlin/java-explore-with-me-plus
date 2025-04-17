package ewm.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Трансферный объект, содержащий данные для добавления новой категории.
 */
@AllArgsConstructor
@Data
public class CreateCategoryDto {
    /**
     * Название категории.
     */
    @NotBlank(message = "Название категории не может быть пустым")
    @Length(max = 50, message = "Название категории не может быть больше 50 символов")
    private String name;
}
