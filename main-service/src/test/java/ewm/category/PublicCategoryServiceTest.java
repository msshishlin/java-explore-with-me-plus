package ewm.category;

import ewm.MainServiceApplication;
import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional(readOnly = true)
@SpringBootTest(classes = {MainServiceApplication.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PublicCategoryServiceTest {
    public final PublicCategoryService publicCategoryService;

    private static List<CategoryDto> getTestCategories() {
        return List.of(
                new CategoryDto(1L, "Test 1"),
                new CategoryDto(2L, "Test 2"));
    }

    @Test
    @DisplayName("Получить все категории с учетом страниц")
    public void shouldGetCategories() {
        List<CategoryDto> categoryDtos = getTestCategories();

        Optional<List<CategoryDto>> categoryDtoOptional = Optional.of(
                publicCategoryService.getCategories(0, 2));

        assertThat(categoryDtoOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(categoryDtos);

        categoryDtoOptional = Optional.of(
                publicCategoryService.getCategories(1, 1));

        assertThat(categoryDtoOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(List.of(categoryDtos.getLast()));
    }

    @Test
    @DisplayName("Получить категорию по id")
    public void shouldGetCategory() {
        CategoryDto categoryDtoTest = getTestCategories().getFirst();

        CategoryDto categoryDto = publicCategoryService.getCategoryById(categoryDtoTest.getId());

        assertEquals(categoryDto, categoryDtoTest);
        assertThrows(NotFoundException.class,
                () -> publicCategoryService.getCategoryById(-1L));
    }
}
