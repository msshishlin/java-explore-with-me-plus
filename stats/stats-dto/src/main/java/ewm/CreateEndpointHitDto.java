package ewm;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Трансферный объект для сохранения информации о запросе.
 */
@Data
public class CreateEndpointHitDto {
    /**
     * Идентификатор сервиса, в который был отправлен запрос.
     */
    @NotNull
    private final String app;

    /**
     * Адрес запроса.
     */
    @NotNull
    private final String uri;

    /**
     * IP-адрес пользователя, сделавшего запрос.
     */
    @NotNull
    private final String ip;

    /**
     * Дата и время, когда был совершен запрос в формате "yyyy-MM-dd HH:mm:ss".
     */
    @NotNull
    private final String timestamp;
}
