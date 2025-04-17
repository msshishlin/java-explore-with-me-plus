package ewm.category;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewCategoryDtoTest {
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    public void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    public void close() {
        validatorFactory.close();
    }

//    @Test
//    @DisplayName("Имя категории")
//    public void shouldName() {
//        CreateCategoryDto newCategoryDto = new CreateCategoryDto("  ");
//
//        List<ConstraintViolation<CreateCategoryDto>> violations =
//                new ArrayList<>(validator.validate(newCategoryDto));
//        assertFalse(violations.isEmpty(), "Валидация пройдена");
//        assertEquals("Наименование категории не может быть пустым", violations.getFirst().getMessage());
//
//        violations = new ArrayList<>(validator.validate(
//                newCategoryDto.toBuilder()
//                        .name("1".repeat(51))
//                        .build()));
//        assertFalse(violations.isEmpty(), "Валидация пройдена");
//        assertEquals("Наименование категории не может быть больше 50 символов", violations.getFirst().getMessage());
//
//        newCategoryDto.setName("category test");
//        violations = new ArrayList<>(validator.validate(newCategoryDto));
//        assertTrue(violations.isEmpty(), "Валидация не пройдена");
//    }
}
