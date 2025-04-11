package user;


import com.fasterxml.jackson.databind.ObjectMapper;
import ewm.user.AdminUserController;
import ewm.user.CreateUserDto;
import ewm.user.UserDto;
import ewm.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(MockitoExtension.class)
public class AdminUserControllerTest {
    @Mock
    UserService userService;
    @InjectMocks
    private AdminUserController adminUserController;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminUserController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Создание пользователя")
    public void shouldCreateUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto(
                "name",
                "example@mail.ru"
        );
        String createUserDtoJson = objectMapper.writeValueAsString(createUserDto);

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserDtoJson))
                .andExpect(status().isCreated());

        verify(userService, times(1)).createUser(createUserDto);
    }

    @Test
    @DisplayName("Получение списка пользователей")
    public void shouldGetUsers() throws Exception {
        List<UserDto> users = Collections.singletonList(new UserDto(1L, "name", "example@mail.ru"));
        when(userService.getUsers(null, 0, 10)).thenReturn(users);

        mockMvc.perform(get("/admin/users")
                        .param("from", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(users)));

        verify(userService, times(1)).getUsers(null, 0, 10);
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void shouldDeleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/admin/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(userId);
    }
}
