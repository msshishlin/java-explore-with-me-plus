package ewm.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Трансферный объект, содержащий данные о категории.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class CategoryDto {
    /**
     * Уникальный идентификатор категории.
     */
    private long id;

    /**
     * Название категории.
     */
    private String name;
}
