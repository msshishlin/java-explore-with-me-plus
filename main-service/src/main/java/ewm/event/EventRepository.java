package ewm.event;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Контракт хранилища данных для сущности "Событие".
 */
public interface EventRepository extends JpaRepository<Event, Long> {

}
