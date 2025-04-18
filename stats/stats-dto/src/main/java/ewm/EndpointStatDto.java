package ewm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Трансферный объект, содержащий статистику запросов.
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
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
    private final Long hits;
}
