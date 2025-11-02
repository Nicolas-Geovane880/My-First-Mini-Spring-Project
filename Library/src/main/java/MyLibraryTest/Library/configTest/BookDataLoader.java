package MyLibraryTest.Library.configTest;

import MyLibraryTest.Library.entity.Book;
import MyLibraryTest.Library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class BookDataLoader implements CommandLineRunner {

    /*

    Class created to auto insert some books ever run, since the H2 in memory doesn't holds data.

    Note: data.sql was not working properly. Since I'm using dll-auto: update, it wasn't necessary to create the
    schema manually.

    */
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Book> books = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> Book.builder()
                        .name("Book example " + i)
                        .price(5D + i)
                        .authorName("Author example " + i)
                        .releaseYear(2000 + i)
                        .build())
                        .toList();

        bookRepository.saveAll(books);
    }
}
