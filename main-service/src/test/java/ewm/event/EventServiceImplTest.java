package ewm.event;

import com.github.javafaker.Faker;
import ewm.MainServiceApplication;
import ewm.category.CategoryDto;
import ewm.category.CategoryService;
import ewm.category.CreateCategoryDto;
import ewm.user.CreateUserDto;
import ewm.user.UserDto;
import ewm.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = MainServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class EventServiceImplTest {
    /**
     * Сервис для сущности "Категория".
     */
    private final CategoryService categoryService;

    /**
     * Сервис для сущности "Событие".
     */
    private final EventService eventService;

    /**
     * Сервис для сущности "Пользователь".
     */
    private final UserService userService;

    @DisplayName("Создание события")
    @Test
    public void should_CreateEvent_WhenModelIsValid() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .build();
        UserDto userDto = userService.createUser(createUserDto);

        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name("Категория события")
                .build();
        CategoryDto categoryDto = categoryService.createCategory(createCategoryDto);

        CreateEventDto createEventDto = CreateEventDto.builder()
                .title("Заголовок события")
                .annotation("Краткое описание события")
                .description("Полное описание события")
                .eventDate(LocalDateTime.now().plusDays(3))
                .category(categoryDto.getId())
                .location(Location.builder().lat(41.40338f).lon(2.17403f).build())
                .paid(false)
                .participantLimit(0)
                .requestModeration(false)
                .build();
        EventDto eventDto = eventService.createEvent(userDto.getId(), createEventDto);

        assertThat(eventDto, notNullValue());
        assertThat(eventDto.getId(), notNullValue());
        assertThat(eventDto.getCreatedOn(), notNullValue());
        assertThat(eventDto.getInitiator(), notNullValue());
        assertThat(eventDto.getInitiator().getId(), equalTo(userDto.getId()));
        assertThat(eventDto.getInitiator().getName(), equalTo(userDto.getName()));
        assertThat(eventDto.getTitle(), equalTo(createEventDto.getTitle()));
        assertThat(eventDto.getAnnotation(), equalTo(createEventDto.getAnnotation()));
        assertThat(eventDto.getDescription(), equalTo(createEventDto.getDescription()));
        assertThat(eventDto.getEventDate(), equalTo(createEventDto.getEventDate()));
        assertThat(eventDto.getCategory(), notNullValue());
        assertThat(eventDto.getCategory().getId(), equalTo(categoryDto.getId()));
        assertThat(eventDto.getCategory().getName(), equalTo(categoryDto.getName()));
        assertThat(eventDto.getLocation(), notNullValue());
        assertThat(eventDto.getLocation().getLat(), equalTo(createEventDto.getLocation().getLat()));
        assertThat(eventDto.getLocation().getLon(), equalTo(createEventDto.getLocation().getLon()));
        assertThat(eventDto.getPublishedOn(), nullValue());
        assertThat(eventDto.isPaid(), equalTo(createEventDto.isPaid()));
        assertThat(eventDto.getParticipantLimit(), equalTo(createEventDto.getParticipantLimit()));
        assertThat(eventDto.isRequestModeration(), equalTo(createEventDto.isRequestModeration()));
        assertThat(eventDto.getConfirmedRequests(), equalTo(0));
        assertThat(eventDto.getState(), equalTo(EventState.PENDING));
        assertThat(eventDto.getViews(), equalTo(0));
    }

    @DisplayName("Получение событий пользователя")
    @Test
    public void should_GetEvents() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .build();
        UserDto userDto = userService.createUser(createUserDto);

        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name("Категория события")
                .build();
        CategoryDto categoryDto = categoryService.createCategory(createCategoryDto);

        eventService.createEvent(userDto.getId(), CreateEventDto.builder()
                .title("Заголовок события 1")
                .annotation("Краткое описание события 1")
                .description("Полное описание события 1")
                .eventDate(LocalDateTime.now().plusDays(3))
                .category(categoryDto.getId())
                .location(Location.builder().lat(41.40338f).lon(2.17403f).build())
                .paid(false)
                .participantLimit(0)
                .requestModeration(false)
                .build());

        eventService.createEvent(userDto.getId(), CreateEventDto.builder()
                .title("Заголовок события 2")
                .annotation("Краткое описание события 2")
                .description("Полное описание события 2")
                .eventDate(LocalDateTime.now().plusDays(3))
                .category(categoryDto.getId())
                .location(Location.builder().lat(41.40338f).lon(2.17403f).build())
                .paid(false)
                .participantLimit(0)
                .requestModeration(false)
                .build());

        eventService.createEvent(userDto.getId(), CreateEventDto.builder()
                .title("Заголовок события 3")
                .annotation("Краткое описание события 3")
                .description("Полное описание события 3")
                .eventDate(LocalDateTime.now().plusDays(3))
                .category(categoryDto.getId())
                .location(Location.builder().lat(41.40338f).lon(2.17403f).build())
                .paid(false)
                .participantLimit(0)
                .requestModeration(false)
                .build());

        Collection<EventDto> events = eventService.getEvents(userDto.getId(), 0, 10);

        assertThat(events, notNullValue());
        assertThat(events.size(), equalTo(3));
    }

    @DisplayName("Получение события по его идентификатору")
    @Test
    public void should_GetEventById() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .build();
        UserDto userDto = userService.createUser(createUserDto);

        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name("Категория события")
                .build();
        CategoryDto categoryDto = categoryService.createCategory(createCategoryDto);

        CreateEventDto createEventDto = CreateEventDto.builder()
                .title("Заголовок события")
                .annotation("Краткое описание события")
                .description("Полное описание события")
                .eventDate(LocalDateTime.now().plusDays(3))
                .category(categoryDto.getId())
                .location(Location.builder().lat(41.40338f).lon(2.17403f).build())
                .paid(false)
                .participantLimit(0)
                .requestModeration(false)
                .build();
        Long eventId = eventService.createEvent(userDto.getId(), createEventDto).getId();

        EventDto eventDto = eventService.getEventById(userDto.getId(), eventId);

        assertThat(eventDto, notNullValue());
        assertThat(eventDto.getId(), notNullValue());
        assertThat(eventDto.getCreatedOn(), notNullValue());
        assertThat(eventDto.getInitiator(), notNullValue());
        assertThat(eventDto.getInitiator().getId(), equalTo(userDto.getId()));
        assertThat(eventDto.getInitiator().getName(), equalTo(userDto.getName()));
        assertThat(eventDto.getTitle(), equalTo(createEventDto.getTitle()));
        assertThat(eventDto.getAnnotation(), equalTo(createEventDto.getAnnotation()));
        assertThat(eventDto.getDescription(), equalTo(createEventDto.getDescription()));
        assertThat(eventDto.getEventDate(), equalTo(createEventDto.getEventDate()));
        assertThat(eventDto.getCategory(), notNullValue());
        assertThat(eventDto.getCategory().getId(), equalTo(categoryDto.getId()));
        assertThat(eventDto.getCategory().getName(), equalTo(categoryDto.getName()));
        assertThat(eventDto.getLocation(), notNullValue());
        assertThat(eventDto.getLocation().getLat(), equalTo(createEventDto.getLocation().getLat()));
        assertThat(eventDto.getLocation().getLon(), equalTo(createEventDto.getLocation().getLon()));
        assertThat(eventDto.getPublishedOn(), nullValue());
        assertThat(eventDto.isPaid(), equalTo(createEventDto.isPaid()));
        assertThat(eventDto.getParticipantLimit(), equalTo(createEventDto.getParticipantLimit()));
        assertThat(eventDto.isRequestModeration(), equalTo(createEventDto.isRequestModeration()));
        assertThat(eventDto.getConfirmedRequests(), equalTo(0));
        assertThat(eventDto.getState(), equalTo(EventState.PENDING));
        assertThat(eventDto.getViews(), equalTo(0));
    }

    @DisplayName("Обновление события")
    @Test
    public void should_UpdateEvent() {
        Faker faker = new Faker();

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .build();
        UserDto userDto = userService.createUser(createUserDto);

        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name("Категория события")
                .build();
        CategoryDto categoryDto = categoryService.createCategory(createCategoryDto);

        CreateEventDto createEventDto = CreateEventDto.builder()
                .title("Заголовок события")
                .annotation("Краткое описание события")
                .description("Полное описание события")
                .eventDate(LocalDateTime.now().plusDays(3))
                .category(categoryDto.getId())
                .location(Location.builder().lat(41.40338f).lon(2.17403f).build())
                .paid(false)
                .participantLimit(0)
                .requestModeration(false)
                .build();
        Long eventId = eventService.createEvent(userDto.getId(), createEventDto).getId();

        UpdateEventDto updateEventDto = UpdateEventDto.builder()
                .title("Заголовок обновленного события")
                .annotation("Краткое описание обновленного события")
                .description("Полное описание обновленного события")
                .eventDate(LocalDateTime.now().plusDays(5))
                .location(Location.builder().lat(41.40238f).lon(3.17403f).build())
                .paid(true)
                .participantLimit(15)
                .requestModeration(true)
                .build();

        EventDto eventDto = eventService.updateEvent(userDto.getId(), eventId, updateEventDto);

        assertThat(eventDto, notNullValue());
        assertThat(eventDto.getId(), notNullValue());
        assertThat(eventDto.getCreatedOn(), notNullValue());
        assertThat(eventDto.getInitiator(), notNullValue());
        assertThat(eventDto.getInitiator().getId(), equalTo(userDto.getId()));
        assertThat(eventDto.getInitiator().getName(), equalTo(userDto.getName()));
        assertThat(eventDto.getTitle(), equalTo(updateEventDto.getTitle()));
        assertThat(eventDto.getAnnotation(), equalTo(updateEventDto.getAnnotation()));
        assertThat(eventDto.getDescription(), equalTo(updateEventDto.getDescription()));
        assertThat(eventDto.getEventDate(), equalTo(updateEventDto.getEventDate()));
        assertThat(eventDto.getCategory(), notNullValue());
        assertThat(eventDto.getCategory().getId(), equalTo(categoryDto.getId()));
        assertThat(eventDto.getCategory().getName(), equalTo(categoryDto.getName()));
        assertThat(eventDto.getLocation(), notNullValue());
        assertThat(eventDto.getLocation().getLat(), equalTo(updateEventDto.getLocation().getLat()));
        assertThat(eventDto.getLocation().getLon(), equalTo(updateEventDto.getLocation().getLon()));
        assertThat(eventDto.getPublishedOn(), nullValue());
        assertThat(eventDto.isPaid(), equalTo(updateEventDto.getPaid()));
        assertThat(eventDto.getParticipantLimit(), equalTo(updateEventDto.getParticipantLimit()));
        assertThat(eventDto.isRequestModeration(), equalTo(updateEventDto.getRequestModeration()));
        assertThat(eventDto.getConfirmedRequests(), equalTo(0));
        assertThat(eventDto.getState(), equalTo(EventState.PENDING));
        assertThat(eventDto.getViews(), equalTo(0));
    }
}