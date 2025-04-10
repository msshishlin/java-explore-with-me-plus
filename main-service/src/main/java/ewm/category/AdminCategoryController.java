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
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.debug("Вызван метод POST /admin/categories с телом {}", newCategoryDto);
        CategoryDto categoryDto = adminCategoryService.createCategory(newCategoryDto);
        log.debug("Метод POST /admin/categories вернул ответ {}", categoryDto);
        return categoryDto;
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable("catId") Long categoryId,
                                      @RequestBody @Valid CategoryDto categoryDto) {
        log.debug("Вызван метод PATCH /admin/categories/{} с телом {}", categoryId, categoryDto);
        CategoryDto updateCategoryDto = adminCategoryService.updateCategory(categoryId, categoryDto);
        log.debug("Метод PATCH /admin/categories/{} вернул ответ {}", categoryId, updateCategoryDto);
        return updateCategoryDto;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") Long categoryId) {
        log.debug("Вызван метод DELETE /admin/categories/{}", categoryId);
        adminCategoryService.deleteCategory(categoryId);
        log.debug("Метод DELETE /admin/categories/{} успешно выполнен", categoryId);
    }
}
