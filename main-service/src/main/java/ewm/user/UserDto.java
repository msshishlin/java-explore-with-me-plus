package ewm.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String name;

    private String email;
}
