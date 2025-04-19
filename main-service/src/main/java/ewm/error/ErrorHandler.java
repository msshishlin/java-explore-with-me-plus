package ewm.error;

import ewm.exception.NotFoundException;
import ewm.exception.UnknownIpException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolation(final ConstraintViolationException e) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                e.getMessage(),
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(StringUtils.join(errors, ';'))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage());
        return new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                e.getMessage(),
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadable(final HttpMessageNotReadableException e) {
        log.warn(e.getMessage());
        return new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                "Error request body",
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUnknownIp(final UnknownIpException e) {
        log.warn(e.getMessage());
        return new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly IP",
                e.getMessage(),
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleDateTimeParse(final DateTimeParseException e) {
        log.warn("Ошибка формата даты и времени: {}", e.getMessage());
        return new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly format",
                "Incorrectly date time format",
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolation(final DataIntegrityViolationException e) {
        log.warn(e.getMessage());
        return new ApiError(
                HttpStatus.CONFLICT.name(),
                "Integrity constraint has been violated.",
                e.getMessage(),
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError(
                HttpStatus.NOT_FOUND.name(),
                "The required object was not found.",
                e.getMessage(),
                null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(final Exception e) {
        log.warn("Непредвиденная ошибка:", e);
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Unexpected error.",
                "Unexpected error.",
                null);
    }
}
