package com.spring.example.crud.errors;

import com.spring.example.crud.domain.validation.EmailNotUniqueException;
import com.spring.example.crud.domain.validation.InvalidSortException;
import com.spring.example.crud.domain.validation.SimpleError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.example.crud.utils.JsonUtils.toJson;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ValidationHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandlers.class);

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<SimpleError> badRequest(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
            .getAllErrors()
                .stream()
                    .map((error) -> (new SimpleError((FieldError)error)))
                        .collect(Collectors.toList());
    }    

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(EmailNotUniqueException.class)
    public List<SimpleError> badRequest(EmailNotUniqueException exception) {
        return exception.getErrors();
    }    

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<SimpleError> status(ResponseStatusException exception) {
        return ResponseEntity
            .status(exception.getStatus())
                .body(new SimpleError(exception.getReason(), exception.getStatus()));
    }    

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(InvalidSortException.class)
    public List<SimpleError> badRequest(InvalidSortException exception) {
        return exception.getErrors();
    }    

    @ResponseStatus(code = UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public SimpleError unauthorized(AccessDeniedException exception) {
        return new SimpleError(exception.getMessage(), UNAUTHORIZED);
    }
    
    public static void forbidden(HttpServletResponse response) {
        try {
            response.setStatus(FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(toJson(
                new SimpleError("Can't find token on Authorization header.", FORBIDDEN)
            ));
        } catch (Exception exception) {
            LOGGER.error("can't write response error on token expired or invalid exception", exception);
        }
    }
}