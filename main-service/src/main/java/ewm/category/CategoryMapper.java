package ewm.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

/**
 * Маппер для моделей, содержащий информацию о категории.
 */
@Mapper
public interface CategoryMapper {
    /**
     * Экземпляр маппера для моделей, содержащих информацию о категории.
     */
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    /**
     * Преобразовать трансферный объект, содержащий данные для добавления новой категории, в объект категории.
     *
     * @param createCategoryDto трансферный объект, содержащий данные для добавления новой категории.
     * @return объект категории.
     */
    Category toCategory(CreateCategoryDto createCategoryDto);

    /**
     * Преобразовать трансферный объект, содержащий данные для обновления категории, в объект категории.
     *
     * @param updateCategoryDto трансферный объект, содержащий данные для обновления категории.
     * @return объект категории.
     */
    Category toCategory(UpdateCategoryDto updateCategoryDto);

    /**
     * Преобразовать объект категории в трансферный объект, содержащий данные о категории.
     *
     * @param category объект категории.
     * @return трансферный объект, содержащий данные о категории.
     */
    CategoryDto toCategoryDto(Category category);

    /**
     * Преобразовать коллекцию объектов категорий в коллекцию трансферных объектов, содержащих информацию о категориях.
     *
     * @param categories коллекцию объектов категорий.
     * @return коллекция трансферных объектов, содержащих информацию о категориях.
     */
    Collection<CategoryDto> toCategoryDtoList(Collection<Category> categories);
}
