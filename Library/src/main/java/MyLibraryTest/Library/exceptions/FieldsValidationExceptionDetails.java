package MyLibraryTest.Library.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FieldsValidationExceptionDetails extends ExceptionDetails {

    private String fields;

    private String fieldsMessage;
}
