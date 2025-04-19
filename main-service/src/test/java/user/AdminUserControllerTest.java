package user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import ewm.user.AdminUserController;
import ewm.user.CreateUserDto;
import ewm.user.UserDto;
import ewm.user.UserService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @DisplayName("Запрос на создание пользователя без тела запроса")
    @SneakyThrows
    @Test
    public void should_ThrowHttpMessageNotReadableExceptionAndReturnBadRequest_WhenBodyIsNull() {
        mockMvc.perform(post("/admin/users"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(HttpMessageNotReadableException.class, result.getResolvedException());
                    assertEquals("Required request body is missing: public ewm.user.UserDto ewm.user.AdminUserController.createUser(ewm.user.CreateUserDto)", result.getResolvedException().getMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя без имени")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsNull() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(faker.internet().emailAddress())
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("name"));

                    assertEquals("Имя пользователя не может быть пустым", Objects.requireNonNull(bindingResult.getFieldError("name")).getDefaultMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя с пустым именем")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsWhitespace() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(" ")
                .email(faker.internet().emailAddress())
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(2, bindingResult.getErrorCount());
                    assertEquals(2, bindingResult.getFieldErrorCount("name"));

                    Collection<String> nameErrors = bindingResult.getFieldErrors("name").stream().map(FieldError::getDefaultMessage).toList();
                    assertTrue(nameErrors.contains("Имя пользователя не может быть пустым"));
                    assertTrue(nameErrors.contains("Имя пользователя не может быть меньше 2 символов"));
                });
    }

    @DisplayName("Запрос на создание пользователя со слишком коротким именем")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsTooShort() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("А")
                .email(faker.internet().emailAddress())
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("name"));

                    assertEquals("Имя пользователя не может быть меньше 2 символов", Objects.requireNonNull(bindingResult.getFieldError("name")).getDefaultMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя со слишком длинным именем")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsTooLong() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(StringUtils.repeat("А", 101))
                .email(faker.internet().emailAddress())
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("name"));

                    assertEquals("Имя пользователя не может быть больше 100 символов", Objects.requireNonNull(bindingResult.getFieldError("name")).getDefaultMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя без адреса электронной почты")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsNull() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("email"));

                    assertEquals("Email пользователя не может быть пустым", Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage());
                });
    }


    @DisplayName("Запрос на создание пользователя с пустым адресом электронной почты")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsWhitespace() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(" ")
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(3, bindingResult.getErrorCount());
                    assertEquals(3, bindingResult.getFieldErrorCount("email"));

                    Collection<String> emailErrors = bindingResult.getFieldErrors("email").stream().map(FieldError::getDefaultMessage).toList();
                    assertTrue(emailErrors.contains("Email пользователя не может быть пустым"));
                    assertTrue(emailErrors.contains("Email пользователя не может быть меньше 6 символов"));
                    assertTrue(emailErrors.contains("Email пользователя должен корректным"));
                });
    }

    @DisplayName("Запрос на создание пользователя со слишком коротким адресом электронной почты")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsTooShort() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email("a@a.a")
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("email"));

                    assertEquals("Email пользователя не может быть меньше 6 символов", Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя со слишком длинным адресом электронной почты")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsTooLong() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(StringUtils.repeat("a", 250) + "aa@aaa.aa")
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("email"));

                    assertEquals("Email пользователя должен корректным", Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя со некорректным адресом электронной почты")
    @SneakyThrows
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsIncorrect() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(StringUtils.repeat("a", 25))
                .build();

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());

                    BindingResult bindingResult = ((MethodArgumentNotValidException) result.getResolvedException()).getBindingResult();

                    assertEquals(1, bindingResult.getErrorCount());
                    assertEquals(1, bindingResult.getFieldErrorCount("email"));

                    assertEquals("Email пользователя должен корректным", Objects.requireNonNull(bindingResult.getFieldError("email")).getDefaultMessage());
                });
    }

    @DisplayName("Запрос на создание пользователя с валидным телом запроса")
    @SneakyThrows
    @Test
    public void should_CreateUser_WhenNameAndEmailAreValid() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();

        when(userService.createUser(createUserDto)).thenReturn(userDto);

        MockHttpServletRequestBuilder requestBuilder = post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));

        verify(userService, times(1)).createUser(createUserDto);
    }

    @DisplayName("Запрос на получение списка пользователей")
    @SneakyThrows
    @Test
    public void shouldGetUsers() {
        List<UserDto> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();

            users.add(UserDto.builder()
                    .id(i + 1)
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .build());
        }

        when(userService.getUsers(null, 0, 10)).thenReturn(users);

        MockHttpServletRequestBuilder requestBuilder = get("/admin/users");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));

        verify(userService, times(1)).getUsers(null, 0, 10);
    }

    @DisplayName("Удаление пользователя")
    @SneakyThrows
    @Test
    public void shouldDeleteUser() {
        long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        MockHttpServletRequestBuilder requestBuilder = delete("/admin/users/{id}", userId);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(userId);
    }
}
