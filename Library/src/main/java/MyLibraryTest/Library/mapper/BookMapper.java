package MyLibraryTest.Library.mapper;

import MyLibraryTest.Library.dto.BookPostRequest;
import MyLibraryTest.Library.dto.BookPutRequest;
import MyLibraryTest.Library.dto.BookResponse;
import MyLibraryTest.Library.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity (BookPostRequest request);

    Book toEntity (BookPutRequest request);

    BookResponse toResponse (Book entity);
}
