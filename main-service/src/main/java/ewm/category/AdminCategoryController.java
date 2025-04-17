package ewm.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    /**
     * Сервис для сущности "Категория".
     */
    private final CategoryService categoryService;

    /**
     * Добавить новую категорию.
     *
     * @param createCategoryDto трансферный объект, содержащий данные для добавления новой категории.
     * @return трансферный объект, содержащий данные о категории.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryDto createCategoryDto) {
        return categoryService.createCategory(createCategoryDto);
    }

    /**
     * Обновить категорию.
     *
     * @param categoryId        идентификатор категории.
     * @param updateCategoryDto трансферный объект, содержащий данные для обновления категории.
     * @return трансферный объект, содержащий данные о категории.
     */
    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable("catId") Long categoryId,
                                      @RequestBody @Valid UpdateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(categoryId, updateCategoryDto);
    }

    /**
     * Удалить категорию.
     *
     * @param categoryId идентификатор категории.
     */
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
