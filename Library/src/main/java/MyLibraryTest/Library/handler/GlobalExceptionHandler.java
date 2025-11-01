package MyLibraryTest.Library.handler;

import MyLibraryTest.Library.exceptions.*;
import jakarta.annotation.Nullable;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (BookNotFoundException.class)
    public ResponseEntity<RequestExceptionDetails> handleBookNotFoundException
            (BookNotFoundException e) {

        return new ResponseEntity<> (RequestExceptionDetails.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .title("Book not found.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (InvalidRequestValuesException.class)
    public ResponseEntity<RequestExceptionDetails> handleInvalidRequestValuesException
            (InvalidRequestValuesException e) {

        return new ResponseEntity<> (RequestExceptionDetails.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid POST values")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException e, @Nullable HttpHeaders headers, @Nullable HttpStatusCode status, @Nullable WebRequest request) {

        List<FieldError> fieldErrors = e.getFieldErrors();

        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        String fieldsMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<> (FieldsValidationExceptionDetails.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid fields.")
                .message("Some field are invalid.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")))
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal
            (Exception e, @Nullable Object body, @Nullable HttpHeaders headers, HttpStatusCode status, @Nullable WebRequest request) {

        ExceptionDetails build = ExceptionDetails.builder()
                .status(status.value())
                .title(e.getMessage())
                .message("An error occur in the JSON format.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")))
                .build();

        return new ResponseEntity<>(build, headers, status);
    }
}


