package MyLibraryTest.Library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPutRequest {

    private long id;

    private String name;

    private Double price;

    private String authorName;

    private Integer releaseYear;
}
