package MyLibraryTest.Library.handler;

import MyLibraryTest.Library.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (BookNotFoundException.class)
    public ResponseEntity<RequestExceptionDetails> bookNotFoundExceptionHandler 
            (BookNotFoundException e) {

        return new ResponseEntity<> (RequestExceptionDetails.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .title("Book not found.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (InvalidRequestValuesException.class)
    public ResponseEntity<RequestExceptionDetails> invalidRequestValuesExceptionHandler 
            (InvalidRequestValuesException e) {

        return new ResponseEntity<> (RequestExceptionDetails.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid POST values")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")))
                .build(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<FieldsValidationExceptionDetails> methodArgumentNotValidExceptionHandler
            (MethodArgumentNotValidException e) {

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
}


