package ewm;

import lombok.Builder;
import lombok.Data;

/**
 * Трансферный объект, содержащий статистику запросов.
 */
@Builder(toBuilder = true)
@Data
public class EndpointStatDto {
    /**
     * Идентификатор сервиса, в который был отправлен запрос.
     */
    private final String app;

    /**
     * Адрес запроса.
     */
    private final String uri;

    /**
     * Количество запросов.
     */
    private final int hits;
}
