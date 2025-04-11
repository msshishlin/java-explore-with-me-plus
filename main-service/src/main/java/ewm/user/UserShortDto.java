package ewm.user;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class UserShortDto {
    private final Long id;
    private final String name;
}
