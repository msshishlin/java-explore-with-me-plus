package ewm.compilations;

import java.util.Collection;
import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto dto);

    void delete(Long compId);

    CompilationDto update(Long compId, UpdateCompilationRequest dto);

    CompilationDto getById(Long id);

    Collection<CompilationDto> getAll(Boolean pinned, int from, int size);
}
