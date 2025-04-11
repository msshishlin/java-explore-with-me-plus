package ewm.user;

import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto);

    void deleteUser(Long id);

    List<UserDto> getUsers(List<Long> ids, int from, int size);
}
