package ewm.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewm.MainServiceApplication;
import ewm.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicCategoryController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainServiceApplication.class})
public class PublicCategoryControllerTest {
    @MockBean
    private PublicCategoryService publicCategoryService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private static List<CategoryDto> getTestCategories() {
        return List.of(
                new CategoryDto(1L, "Test 1"),
                new CategoryDto(2L, "Test 2"));
    }

    @Test
    @DisplayName("Получить все категории с учетом страниц")
    public void shouldGetCategories() throws Exception {
        int from = 0;
        int size = 2;
        List<CategoryDto> categoryDtos = getTestCategories();

        when(publicCategoryService.getCategories(from, size)).thenReturn(categoryDtos);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from", String.valueOf(from));
        params.add("size", String.valueOf(size));
        mvc.perform(get("/categories")
                        .params(params)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(categoryDtos)));

        verify(publicCategoryService, times(1)).getCategories(from, size);
    }

    @Test
    @DisplayName("Получить категорию по id")
    public void shouldGetCategory() throws Exception {
        CategoryDto category = getTestCategories().getFirst();

        when(publicCategoryService.getCategoryById(category.getId())).thenReturn(category);

        mvc.perform(get("/categories/" + category.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(category)));

        when(publicCategoryService.getCategoryById(-1L)).thenThrow(NotFoundException.class);

        mvc.perform(get("/categories/-1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(publicCategoryService, times(1)).getCategoryById(category.getId());
        verify(publicCategoryService, times(1)).getCategoryById(-1L);
    }
}
