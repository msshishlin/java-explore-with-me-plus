package ewm.event;

/**
 * Состояние события.
 */
public enum EventState {
    /**
     * В состоянии ожидания.
     */
    PENDING,

    /**
     * Опубликовано.
     */
    PUBLISHED,

    /**
     * Отменено.
     */
    CANCELED
}
