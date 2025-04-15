package ewm.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class CreateUserDto {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Length(min = 2, message = "Имя пользователя не может быть меньше 2 символов")
    @Length(max = 250, message = "Имя пользователя не может быть больше 250 символов")
    private String name;

    @NotBlank(message = "Email пользователя не может быть пустым")
    @Email(message = "Email пользователя должен корректным")
    @Length(min = 6, message = "Email пользователя не может быть меньше 6 символов")
    @Length(max = 254, message = "Email пользователя не может быть больше 254 символов")
    private String email;
}
