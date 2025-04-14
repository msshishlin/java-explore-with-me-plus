package ewm.user;

import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        User user = UserMapper.INSTANCE.toUser(createUserDto);
        return UserMapper.INSTANCE.toUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<User> users;
        if (ids != null && !ids.isEmpty()) {
            users = userRepository.findAllById(ids);
        } else {
            users = userRepository.findAll(pageRequest).getContent();
        }
        return UserMapper.INSTANCE.toListUserDto(users);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Не найден пользователь с id: " + id));
        userRepository.deleteById(id);
    }
}
