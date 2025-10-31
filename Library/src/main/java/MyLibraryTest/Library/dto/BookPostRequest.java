package MyLibraryTest.Library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPostRequest {

    @NotNull(message = "Book name cannot be null")
    @Size (min = 3, max = 60, message = "Book name must be between 3 and 60 characters")
    private String name;

    @NotNull (message = "Price cannot be null")
    @Min (value = 3, message = "Price must be between 3 and 999")
    @Max (value = 999, message = "Price must be between 3 and 999")
    private Double price;

    @NotNull (message = "Author name cannot be null")
    @Size (min = 3, max = 60, message = "Author name must be between 3 and 60 characters")
    private String authorName;

    @NotNull (message = "Book release year cannot be null")
    @Min (value = 1800, message = "Book release year must be between 1800 and 2025")
    @Max (value = 2025, message = "Book release year must be between 1800 and 2025")
    private Integer releaseYear;
}
