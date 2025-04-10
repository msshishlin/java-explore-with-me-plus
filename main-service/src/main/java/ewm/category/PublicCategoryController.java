package ewm.category;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final PublicCategoryService publicCategoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод GET /categories с from = {} и size = {}", from, size);
        List<CategoryDto> categoryDto = publicCategoryService.getCategories(from, size);
        log.debug("Метод GET /categories вернул ответ {}", categoryDto);
        return categoryDto;
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable("catId") Long categoryId) {
        log.debug("Вызван метод GET /categories/{}", categoryId);
        CategoryDto categoryDto = publicCategoryService.getCategoryById(categoryId);
        log.debug("Метод GET /categories/{} вернул ответ {}", categoryId, categoryDto);
        return categoryDto;
    }
}