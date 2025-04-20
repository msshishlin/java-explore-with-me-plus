package user;

import com.github.javafaker.Faker;
import ewm.MainServiceApplication;
import ewm.exception.NotFoundException;
import ewm.user.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = MainServiceApplication.class)
@Transactional
public class UserServiceImplTest {
    /**
     * Хранилище данных для сущности "Пользователь".
     */
    private final UserRepository userRepository;

    /**
     * Сервис для сущности "Пользователь".
     */
    private final UserService userService;

    @DisplayName("Создание пользователя без имени")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsNull() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(faker.internet().emailAddress())
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Значение NULL не разрешено для поля \"NAME\""));
    }

    @DisplayName("Создание пользователя с пустым именем")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsWhitespace() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(" ")
                .email(faker.internet().emailAddress())
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Нарушение ограничения: \"USERS_NAME_CHECK: \""));
    }

    @DisplayName("Создание пользователя со слишком коротким именем")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsTooShort() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("А")
                .email(faker.internet().emailAddress())
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Нарушение ограничения: \"USERS_NAME_CHECK: \""));
    }

    @DisplayName("Создание пользователя со слишком длинным именем")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenNameIsTooLong() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(StringUtils.repeat("А", 101))
                .email(faker.internet().emailAddress())
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Значение слишком длинное для поля \"NAME CHARACTER VARYING(100)\""));
    }

    @DisplayName("Создание пользователя без адреса электронной почты")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsNull() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Значение NULL не разрешено для поля \"EMAIL\""));
    }

    @DisplayName("Создание пользователя с пустым адресом электронной почты")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsWhitespace() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(" ")
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Нарушение ограничения: \"USERS_EMAIL_CHECK: \""));
    }

    @DisplayName("Создание пользователя со слишком коротким адресом электронной почты")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsTooShort() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email("a@a.a")
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Нарушение ограничения: \"USERS_EMAIL_CHECK: \""));
    }

    @DisplayName("Создание пользователя со слишком длинным адресом электронной почты")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsTooLong() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(StringUtils.repeat("a", 250) + "aa@aaa.aa")
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Значение слишком длинное для поля \"EMAIL CHARACTER VARYING(254)\""));
    }

    @DisplayName("Создание пользователя с некорректным адресом электронной почты")
    @Test
    public void should_ThrowMethodArgumentNotValidExceptionAndReturnBadRequest_WhenEmailIsIncorrect() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(StringUtils.repeat("a", 25))
                .build();

        assertThat(assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createUserDto)).getMessage(), containsString("Нарушение ограничения: \"USERS_EMAIL_CHECK: \""));
    }

    @DisplayName("Создание пользователя")
    @Test
    public void should_CreateUser_WhenNameAndEmailAreValid() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();

        UserDto userDto = userService.createUser(createUserDto);

        assertThat(userDto.getId(), notNullValue());
        assertThat(userDto.getName(), equalTo(createUserDto.getName()));
        assertThat(userDto.getEmail(), equalTo(createUserDto.getEmail()));
    }

    private static Stream<Arguments> dataSourceFor_Should_GetUsers_WhenUserIdsIsNull() {
        return Stream.of(
                Arguments.of(0, 0, 5, 0),
                Arguments.of(0, 5, 5, 0),
                Arguments.of(10, 0, 5, 5),
                Arguments.of(10, 5, 5, 5),
                Arguments.of(10, 5, 10, 5),
                Arguments.of(10, 0, 15, 10),
                Arguments.of(10, 15, 15, 0)
        );
    }

    @DisplayName("Получение пользователей постранично")
    @MethodSource("dataSourceFor_Should_GetUsers_WhenUserIdsIsNull")
    @ParameterizedTest
    public void should_GetUsers_WhenUserIdsIsNull(int generatedUsersCount, int from, int size, int expectedCount) {
        List<User> users = new ArrayList<>();

        for (long i = 0; i < generatedUsersCount; i++) {
            Faker faker = new Faker();

            users.add(User.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .build());
        }

        userRepository.saveAll(users);

        Collection<UserDto> usersDto = userService.getUsers(null, from, size);

        assertThat(usersDto, notNullValue());
        assertThat(usersDto.size(), equalTo(expectedCount));
    }

    private static Stream<Arguments> dataSourceFor_Should_GetUsers_WhenUserIdsIsNotNull() {
        return Stream.of(
                Arguments.of(10, List.of(1, 2), 0, 10, 2),
                Arguments.of(10, List.of(9, 10), 0, 10, 1),
                Arguments.of(10, List.of(10, 11), 0, 10, 10)
        );
    }

    @DisplayName("Получение пользователей по их идентификаторам")
    @MethodSource("dataSourceFor_Should_GetUsers_WhenUserIdsIsNotNull")
    @ParameterizedTest
    public void should_GetUsers_WhenUserIdsIsNotNull(int generatedUsersCount, Collection<Integer> usersToExtract, int from, int size, int expectedCount) {
        List<User> users = new ArrayList<>();

        for (long i = 0; i < generatedUsersCount; i++) {
            Faker faker = new Faker();

            users.add(User.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .build());
        }

        userRepository.saveAll(users);

        Collection<Long> userIds = new ArrayList<>();

        for (int userToExtract : usersToExtract) {
            if (userToExtract < users.size()) {
                userIds.add(users.get(userToExtract).getId());
            }
        }

        Collection<UserDto> usersDto = userService.getUsers(userIds, from, size);

        assertThat(usersDto, notNullValue());
        assertThat(usersDto.size(), equalTo(expectedCount));
    }

    @DisplayName("Удаление несуществующего пользователя")
    @Test
    public void should_ThrowNotFoundException_WhenDeletingNonExistingUser() {
        Faker faker = new Faker();

        User user = User.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();

        userRepository.save(user);
        assertThat(assertThrows(NotFoundException.class, () -> userService.deleteUser(user.getId() + 1)).getMessage(), equalTo(String.format("Пользователь с id = %d не найден", user.getId() + 1)));
    }

    @DisplayName("Удаление пользователя")
    @Test
    public void should_DeleteUser_WhenDeletingUserIsExist() {
        Faker faker = new Faker();

        User user = User.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();

        userRepository.save(user);
        userService.deleteUser(user.getId());

        assertThat(userRepository.findById(user.getId()).isEmpty(), equalTo(true));
    }
}
