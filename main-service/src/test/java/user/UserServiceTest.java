package user;

import ewm.MainServiceApplication;
import ewm.exception.NotFoundException;
import ewm.user.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MainServiceApplication.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;

    @Test
    @DisplayName("Создать ресурс user")
    public void shouldCreateEndpointHit() throws UnknownHostException {
        User user = new User(
                2L,
                "Name",
                "Example@mail.ru"
        );

        CreateUserDto createUserDto = new CreateUserDto(
                user.getName(),
                user.getEmail()
        );

        UserDto endpointHitDto = userService.createUser(createUserDto);

        assertEquals(endpointHitDto, UserMapper.INSTANCE.toUserDto(user));
    }

    @Test
    @DisplayName("Получить пользователей по ids")
    public void shouldGetUsersByIds() {
        User user1 = new User(2L, "User1", "user1@mail.com");
        User user2 = new User(3L, "User2", "user2@mail.com");
        userRepository.saveAll(Arrays.asList(user1, user2));

        List<UserDto> usersDto = userService.getUsers(null, 0, 1);

        assertNotNull(usersDto);
        assertEquals(1, usersDto.size());
    }

    @Test
    @DisplayName("Получить пользователей с пагинацией")
    public void shouldGetUsersWithPagination() {
        User user1 = new User(2L, "User1", "user1@mail.com");
        User user2 = new User(3L, "User2", "user2@mail.com");
        userRepository.saveAll(Arrays.asList(user1, user2));

        List<UserDto> usersDto = userService.getUsers(null, 0, 1);

        assertNotNull(usersDto);
        assertEquals(1, usersDto.size());
    }

    @Test
    @DisplayName("Удалить пользователя")
    public void shouldDeleteUser() {
        User user = new User(2L, "Name", "Example@mail.ru");
        userRepository.save(user);

        userService.deleteUser(2L);

        Optional<User> deletedUser = userRepository.findById(2L);
        assertFalse(deletedUser.isPresent(), "Пользователь должен быть удален");
    }

    @Test
    @DisplayName("Попытка удалить несуществующего пользователя")
    public void shouldThrowExceptionWhenDeletingNonExistentUser() {
        Long nonIdUser = 999L;

        assertThrows(NotFoundException.class, () -> {
            userService.deleteUser(nonIdUser);
        }, "Ожидается ошибка при удалении пользователя");
    }
}
