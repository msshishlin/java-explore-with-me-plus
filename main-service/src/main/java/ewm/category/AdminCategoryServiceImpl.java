package ewm.category;

import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.INSTANCE.toCategory(newCategoryDto);
        return CategoryMapper.INSTANCE.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = findCategoryById(categoryId);
        Category updateCategory = CategoryMapper.INSTANCE.toCategory(categoryDto);
        updateCategory.setId(category.getId());
        updateCategory.setName(
                updateCategory.getName() != null && !updateCategory.getName().isBlank()
                        ? updateCategory.getName()
                        : category.getName());
        return CategoryMapper.INSTANCE.toCategoryDto(categoryRepository.save(updateCategory));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);
        //TODO Добавить проверку на события когда они появятся
        categoryRepository.delete(category);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + categoryId + " was not found"));
    }
}
