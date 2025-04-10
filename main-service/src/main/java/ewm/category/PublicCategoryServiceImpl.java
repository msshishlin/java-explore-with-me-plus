package ewm.category;

import ewm.exception.NotFoundException;
import ewm.pageble.PageOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Pageable pageable = PageOffset.of(from, size, Sort.by("id").ascending());
        return CategoryMapper.INSTANCE.toCategoryDtoList(categoryRepository.findAll(pageable).getContent());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        return CategoryMapper.INSTANCE.toCategoryDto(
                categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new NotFoundException("Category with id=" + categoryId + " was not found")));
    }
}
