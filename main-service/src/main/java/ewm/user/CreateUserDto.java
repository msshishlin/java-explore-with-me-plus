package ewm.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDto {
    @NotNull(message = "Имя пользователя не может быть пустым")
    private String name;
    @Email(message = "Email пользователя должен корректным")
    private String email;
}
