package ewm;

import lombok.Builder;
import lombok.Data;

/**
 * Трансферный объект, содержащий информацию о запросе.
 */
@Builder(toBuilder = true)
@Data
public class EndpointHitDto {
    /**
     * Идентификатор записи.
     */
    private final int id;

    /**
     * Идентификатор сервиса, в который был отправлен запрос.
     */
    private final String app;

    /**
     * Адрес запроса.
     */
    private final String uri;

    /**
     * IP-адрес пользователя, сделавшего запрос.
     */
    private final String ip;

    /**
     * Дата и время, когда был совершен запрос в формате "yyyy-MM-dd HH:mm:ss".
     */
    private final String timestamp;
}
