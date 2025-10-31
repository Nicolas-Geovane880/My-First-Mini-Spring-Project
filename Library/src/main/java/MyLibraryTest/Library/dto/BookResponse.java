package MyLibraryTest.Library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private String name;

    private Double price;

    private String authorName;

    private Integer releaseYear;
}
