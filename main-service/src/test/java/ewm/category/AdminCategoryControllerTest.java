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

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminCategoryController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainServiceApplication.class})
class AdminCategoryControllerTest {
//    @MockBean
//    private AdminCategoryService adminCategoryService;
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private ObjectMapper mapper;
//
//    private static CategoryDto getCategoryTest() {
//        return new CategoryDto(1L, "Test category");
//    }
//
//    @Test
//    @DisplayName("Добавление категории")
//    void shouldCreateCategory() throws Exception {
//        CategoryDto categoryDto = getCategoryTest();
//        CreateCategoryDto newCategoryDto = new CreateCategoryDto(categoryDto.getName());
//
//        when(adminCategoryService.createCategory(newCategoryDto)).thenReturn(categoryDto);
//
//        mvc.perform(post("/admin/categories")
//                        .content(mapper.writeValueAsString(newCategoryDto))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().json(mapper.writeValueAsString(categoryDto)));
//
//        mvc.perform(post("/admin/categories")
//                        .content(mapper.writeValueAsString(new CreateCategoryDto("   ")))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        mvc.perform(post("/admin/categories")
//                        .content(mapper.writeValueAsString(new CreateCategoryDto("1".repeat(51))))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        verify(adminCategoryService, times(1)).createCategory(newCategoryDto);
//    }

//    @Test
//    @DisplayName("Изменение категории")
//    void shouldUpdateCategory() throws Exception {
//        CategoryDto categoryDto = getCategoryTest();
//
//        when(adminCategoryService.updateCategory(categoryDto.getId(), categoryDto)).thenReturn(categoryDto);
//
//        mvc.perform(patch("/admin/categories/" + categoryDto.getId())
//                        .content(mapper.writeValueAsString(categoryDto))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(categoryDto)));
//
//        when(adminCategoryService.updateCategory(-1L, categoryDto)).thenThrow(NotFoundException.class);
//
//        mvc.perform(patch("/admin/categories/-1")
//                        .content(mapper.writeValueAsString(categoryDto))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        verify(adminCategoryService, times(1)).updateCategory(categoryDto.getId(), categoryDto);
//        verify(adminCategoryService, times(1)).updateCategory(-1L, categoryDto);
//    }

//    @Test
//    @DisplayName("Удаление категории")
//    void shouldDeleteCategory() throws Exception {
//        CategoryDto categoryDto = getCategoryTest();
//
//        doNothing().when(adminCategoryService).deleteCategory(categoryDto.getId());
//
//        mvc.perform(delete("/admin/categories/" + categoryDto.getId())
//                        .characterEncoding(StandardCharsets.UTF_8))
//                .andExpect(status().is2xxSuccessful());
//
//        doThrow(NotFoundException.class).when(adminCategoryService).deleteCategory(-1L);
//
//        mvc.perform(delete("/admin/categories/-1")
//                        .characterEncoding(StandardCharsets.UTF_8))
//                .andExpect(status().is4xxClientError());
//
//        verify(adminCategoryService, times(1)).deleteCategory(categoryDto.getId());
//        verify(adminCategoryService, times(1)).deleteCategory(-1L);
//    }
}