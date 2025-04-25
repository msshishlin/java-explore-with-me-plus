package ewm.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/compilations")
@RequiredArgsConstructor
@RestController
@Validated
public class PublicCompilationController {
    CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId){
        return compilationService.getById(compId);
    }
}
