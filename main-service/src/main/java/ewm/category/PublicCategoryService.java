package ewm.category;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(Long categoryId);
}
