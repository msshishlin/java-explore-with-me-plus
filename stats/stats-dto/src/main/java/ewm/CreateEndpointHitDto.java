package ewm;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Трансферный объект для сохранения информации о запросе.
 */
@Data
public class CreateEndpointHitDto {
    /**
     * Идентификатор сервиса, в который был отправлен запрос.
     */
    @NotBlank
    private final String app;

    /**
     * Адрес запроса.
     */
    @NotBlank
    private final String uri;

    /**
     * IP-адрес пользователя, сделавшего запрос.
     */
    @NotBlank
    private final String ip;

    /**
     * Дата и время, когда был совершен запрос в формате "yyyy-MM-dd HH:mm:ss".
     */
    @NotBlank
    private final String timestamp;
}
