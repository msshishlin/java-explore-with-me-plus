package ewm.event;

import ewm.category.Category;
import ewm.category.CategoryRepository;
import ewm.exception.ForbiddenException;
import ewm.exception.NotFoundException;
import ewm.pageble.PageOffset;
import ewm.user.User;
import ewm.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * Сервис для сущности "Событие".
 */
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    /**
     * Хранилище данных для сущности "Категория".
     */
    private final CategoryRepository categoryRepository;

    /**
     * Хранилище данных для сущности "Событие".
     */
    private final EventRepository eventRepository;

    /**
     * Хранилище данных для сущности "Пользователь".
     */
    private final UserRepository userRepository;

    /**
     * Добавить новое событие.
     *
     * @param userId         идентификатор текущего пользователя.
     * @param createEventDto трансферный объект, содержащий данные для добавления нового события.
     * @return трансферный объект, содержащий данные о событии.
     */
    public EventDto createEvent(Long userId, CreateEventDto createEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", userId)));
        Category category = categoryRepository.findById(createEventDto.getCategory()).orElseThrow(() -> new NotFoundException(String.format("Категория с id = %d не найдена", createEventDto.getCategory())));

        Event event = EventMapper.INSTANCE.toEvent(createEventDto);
        event.setInitiator(user);
        event.setCategory(category);

        return EventMapper.INSTANCE.toEventDto(eventRepository.save(event));
    }

    /**
     * Получить коллекцию событий.
     *
     * @param userId идентификатор пользователя.
     * @param from   количество событий, которое необходимо пропустить.
     * @param size   количество событий, которое необходимо получить.
     * @return коллекция событий.
     */
    public Collection<EventDto> getEvents(Long userId, int from, int size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", userId)));

        Example<Event> example = Example.of(Event.builder()
                .initiator(User.builder().id(userId).build())
                .build());

        PageOffset pageOffset = PageOffset.of(from, size, Sort.by("id").ascending());

        return EventMapper.INSTANCE.toEventDtoCollection(eventRepository.findAll(example, pageOffset).getContent());
    }

    /**
     * Получить событие по его идентификатору.
     *
     * @param userId  идентификатор пользователя.
     * @param eventId идентификатор события.
     * @return событие.
     */
    public EventDto getEventById(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", userId)));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id = %d не найдено", eventId)));

        if (!Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ForbiddenException(String.format("Доступ к событию с id = %d запрещён", eventId));
        }

        return EventMapper.INSTANCE.toEventDto(event);
    }

    /**
     * Обновить событие.
     *
     * @param userId         идентификатор текущего пользователя.
     * @param eventId        идентификатор события.
     * @param updateEventDto трансферный объект, содержащий данные для обновления события.
     * @return трансферный объект, содержащий данные о событии.
     */
    public EventDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", userId)));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id = %d не найдено", eventId)));

        if (!Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ForbiddenException(String.format("Доступ к событию с id = %d запрещён", eventId));
        }

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException(String.format("Событие с id = %d запрещено для редактирования", eventId));
        }

        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }

        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }

        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }

        if (updateEventDto.getEventDate() != null) {
            event.setEventDate(updateEventDto.getEventDate());
        }

        if (updateEventDto.getCategory() != null) {
            Category category = categoryRepository.findById(updateEventDto.getCategory()).orElseThrow(() -> new NotFoundException(String.format("Категория с id = %d не найдена", updateEventDto.getCategory())));
            event.setCategory(category);
        }

        if (updateEventDto.getLocation() != null) {
            event.setLocation(updateEventDto.getLocation());
        }

        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }

        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }

        if (updateEventDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventDto.getRequestModeration());
        }

        if (updateEventDto.getStateAction() != null) {
            if (updateEventDto.getStateAction() == EventStateAction.SEND_TO_REVIEW) {
                event.setState(EventState.PENDING);
            }
            if (updateEventDto.getStateAction() == EventStateAction.CANCEL_REVIEW) {
                event.setState(EventState.CANCELED);
            }
        }

        return EventMapper.INSTANCE.toEventDto(eventRepository.save(event));
    }
}
