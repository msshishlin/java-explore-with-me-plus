package ewm.category;

import ewm.MainServiceApplication;
import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = {MainServiceApplication.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AdminCategoryServiceTest {
//    private final AdminCategoryService adminCategoryService;
//    private final CategoryRepository categoryRepository;
//
//    private static CategoryDto getCategoryTest() {
//        return new CategoryDto(1L, "Test category");
//    }
//
//    @Test
//    @DisplayName("Добавление категории")
//    void shouldCreateCategory() {
//        CategoryDto categoryDtoTest = getCategoryTest();
//        CreateCategoryDto newCategoryDto = new CreateCategoryDto(categoryDtoTest.getName());
//
//        CategoryDto categoryDto = adminCategoryService.createCategory(newCategoryDto);
//
//        assertEquals(categoryDto.getName(), categoryDtoTest.getName());
//        assertThrows(DataIntegrityViolationException.class,
//                () -> adminCategoryService.createCategory(newCategoryDto));
//    }

//    @Test
//    @DisplayName("Изменение категории")
//    void shouldUpdateCategory() {
//        CategoryDto categoryDtoTest = getCategoryTest();
//
//        CategoryDto categoryDto = adminCategoryService.updateCategory(categoryDtoTest.getId(), categoryDtoTest);
//
//        assertEquals(categoryDto.getName(), categoryDtoTest.getName());
//        assertThrows(NotFoundException.class,
//                () -> adminCategoryService.updateCategory(-1L, categoryDtoTest));
//    }

//    @Test
//    @DisplayName("Удаление категории")
//    void shouldDeleteCategory() {
//        CategoryDto categoryDtoTest = getCategoryTest();
//
//        adminCategoryService.deleteCategory(categoryDtoTest.getId());
//        boolean deleteCategory = categoryRepository.findById(categoryDtoTest.getId()).isEmpty();
//
//        assertTrue(deleteCategory);
//        assertThrows(NotFoundException.class,
//                () -> adminCategoryService.deleteCategory(categoryDtoTest.getId()));
//    }
}