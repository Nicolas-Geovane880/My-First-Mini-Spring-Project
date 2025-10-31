package MyLibraryTest.Library.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails {

    protected int status;

    protected String title;

    protected String message;

    protected String timestamp;
}
