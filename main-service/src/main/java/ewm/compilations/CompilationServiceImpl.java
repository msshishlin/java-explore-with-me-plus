package ewm.compilations;

import ewm.pageble.PageOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper mapper = CompilationMapper.INSTANCE;

    @Override
    public CompilationDto create(NewCompilationDto dto) {
        Compilation compilation = mapper.toCompilation(dto, null); // без событий
        return mapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto update(Long compId, UpdateCompilationRequest dto) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new RuntimeException("Compilation not found: " + compId));
        mapper.updateCompilationFromDto(dto, compilation);
        return mapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getById(Long id) {
        return mapper.toDto(compilationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compilation not found: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CompilationDto> getAll(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return mapper.toDtoCollection(
                    compilationRepository.findAll(PageOffset.of(from, size)).getContent()
            );
        }

        return mapper.toDtoCollection(
                compilationRepository.findAllByPinned(pinned, PageOffset.of(from, size)).getContent()
        );
    }
}
