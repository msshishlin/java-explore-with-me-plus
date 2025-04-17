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

public class CategoryDtoTest {
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
//        CategoryDto categoryDto = new CategoryDto(null, "1".repeat(51));
//
//        List<ConstraintViolation<CategoryDto>> violations = new ArrayList<>(validator.validate(categoryDto));
//        assertFalse(violations.isEmpty(), "Валидация пройдена");
//        assertEquals("Наименование категории не может быть больше 50 символов", violations.getFirst().getMessage());
//
//        categoryDto.setName("category test");
//        violations = new ArrayList<>(validator.validate(categoryDto));
//        assertTrue(violations.isEmpty(), "Валидация не пройдена");
//    }
}
