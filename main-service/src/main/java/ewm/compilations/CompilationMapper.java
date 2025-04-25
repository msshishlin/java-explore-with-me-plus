package ewm.compilations;

import ewm.event.Event;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface CompilationMapper {

    CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

    CompilationDto toCompilationDto(Compilation compilation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    Compilation toCompilation(NewCompilationDto dto, Set<Event> events);

    @Mapping(source = "events", target = "events")
    CompilationDto toDto(Compilation compilation);

    List<CompilationDto> toDtoCollection(Collection<Compilation> compilations);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompilationFromDto(UpdateCompilationRequest dto, @MappingTarget Compilation compilation);

    @AfterMapping
    default void updateEvents(UpdateCompilationRequest dto,
                              @MappingTarget Compilation compilation,
                              @Context Set<Event> events) {
        if (dto.getEvents() != null && events != null) {
            compilation.setEvents(events);
        }
    }
}
