package ewm.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Трансферный объект, содержащий данные для добавления нового пользователя.
 */
@Builder(toBuilder = true)
@Data
public class CreateUserDto {
    /**
     * Имя пользователя.
     */
    @Length(min = 2, message = "Имя пользователя не может быть меньше 2 символов")
    @Length(max = 100, message = "Имя пользователя не может быть больше 100 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;

    /**
     * Адрес электронной почты пользователя.
     */
    @Email(message = "Email пользователя должен корректным")
    @Length(min = 6, message = "Email пользователя не может быть меньше 6 символов")
    @NotBlank(message = "Email пользователя не может быть пустым")
    private String email;
}
