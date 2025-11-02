package MyLibraryTest.Library.controller;

import MyLibraryTest.Library.dto.BookPostRequest;
import MyLibraryTest.Library.dto.BookPutRequest;
import MyLibraryTest.Library.dto.BookResponse;
import MyLibraryTest.Library.entity.Book;
import MyLibraryTest.Library.mapper.BookMapper;
import MyLibraryTest.Library.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @GetMapping (path = "/all")
    public ResponseEntity<Page<BookResponse>> findAll (@RequestParam (required = false, defaultValue = "0") int page,
                                                       @RequestParam (required = false, defaultValue = "10") int size) {

        Page<BookResponse> allFound = bookService.findAll(page, size);
        return new ResponseEntity<>(allFound, HttpStatus.OK);
    }

    @GetMapping (path = "/by-author/{authorName}")
    public ResponseEntity<List<BookResponse>> findAllByAuthor (@PathVariable String authorName) {
        List<BookResponse> allFoundByAuthor = bookService.findAllByAuthorName(authorName);

        return new ResponseEntity<>(allFoundByAuthor, HttpStatus.OK);
    }

    @GetMapping (path = "/by-price")
    public ResponseEntity<List<BookResponse>> filterBooksByPrice
            (@RequestParam (defaultValue = "3", required = false) double minPrice,
             @RequestParam (defaultValue = "999", required = false) double maxPrice) {

        List<BookResponse> allFoundByPrice = bookService.filterBookByPrice(minPrice, maxPrice);

        return new ResponseEntity<>(allFoundByPrice, HttpStatus.OK);
    }

    @GetMapping (path = "/search")
    public ResponseEntity<BookResponse> findBy (@RequestParam (required = false) String bookName,
                                                @RequestParam (required = false) Long id) {

        Book foundBook = bookService.findBy(bookName, id);
        BookResponse bookResponse = bookMapper.toResponse(foundBook);

        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create (@Valid @RequestBody BookPostRequest request) {
        Book savedBook = bookService.save(bookMapper.toEntity(request));

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace (@RequestBody BookPutRequest request) {
        bookService.replace(bookMapper.toEntity(request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping (path = "d/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        bookService.deleteById(id);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
