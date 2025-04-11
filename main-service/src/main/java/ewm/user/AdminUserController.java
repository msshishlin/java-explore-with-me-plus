package ewm.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        log.info("Вызван метод POST /admin/users с телом запроса {}", createUserDto);
        UserDto userDto = userService.createUser(createUserDto);
        log.info("Метод POST /admin/users успешно выполнен");
        return userDto;
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") @Min(0) int from,
                                  @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Вызван метод GET /admin/users с параметрами ids: {}, from: {}, size: {}", ids, from, size);
        List<UserDto> userDtos = userService.getUsers(ids, from, size);
        log.info("Метод GET /admin/users успешно выполнен");
        return userDtos;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.info("Вызван метод DELETE /admin/users/{}", id);
        userService.deleteUser(id);
        log.info("Метод DELETE /admin/users/{} успешно выполнен", id);
    }
}
