package ewm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Хранилище данных для сущности "Пользователь".
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
