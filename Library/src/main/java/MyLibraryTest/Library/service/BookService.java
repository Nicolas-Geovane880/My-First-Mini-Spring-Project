package MyLibraryTest.Library.service;

import MyLibraryTest.Library.dto.BookResponse;
import MyLibraryTest.Library.entity.Book;
import MyLibraryTest.Library.exceptions.BookNotFoundException;
import MyLibraryTest.Library.exceptions.InvalidRequestValuesException;
import MyLibraryTest.Library.mapper.BookMapper;
import MyLibraryTest.Library.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public Page<BookResponse> findAll (int page, int size) {
        Page<Book> allPages = bookRepository.findAll(PageRequest.of(page, size));

        List<Book> allBooks = allPages.get().toList();

        if (allBooks.isEmpty()) throw new BookNotFoundException("No book found.");

        List<BookResponse> bookResponses = allBooks.stream().map(b -> BookResponse.builder()
                .name(b.getName())
                .price(b.getPrice())
                .authorName(b.getAuthorName())
                .releaseYear(b.getReleaseYear())
                .build()).toList();

        return new PageImpl<>(bookResponses, PageRequest.of(page, size), bookResponses.size());
    }

    public Book findBy (String name, Long id) {
        if (name != null & id != null) {
            throw new InvalidRequestValuesException ("Provide only the name or id, not both");
        }
        else if (name != null) {
            return findByName(name);
        }
        else if (id != null) {
            return findById(id);
        }
        else {
            throw new InvalidRequestValuesException ("Provide at least one valid value to find it.");
        }
    }

    public Book findByName (String name) {
        if (name == null || name.isEmpty()) throw new InvalidRequestValuesException ("Name cannot be null or empty.");

        return bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BookNotFoundException ("Book with name '%s' not found.".formatted(name)));
    }

    public Book findById (Long id) {
        if (id == null || id <= 0) throw new InvalidRequestValuesException("Id cannot be null, equals or lower than 0");

        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id '%d' not found".formatted(id)));
    }

    public List<BookResponse> findAllByAuthorName (String authorName) {
        List<BookResponse> bookResponses = bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .filter(b -> b.getAuthorName().equalsIgnoreCase(authorName))
                .toList();



        if (bookResponses.isEmpty()) throw new BookNotFoundException("No book found.");

        return bookResponses;
    }

    public List<BookResponse> filterBookByPrice (double minPrice, double maxPrice) {
        List<BookResponse> filteredByPrice = bookRepository.findAll().stream()
                .filter(b -> b.getPrice() >= minPrice && b.getPrice() <= maxPrice)
                .map(bookMapper::toResponse)
                .sorted(Comparator.comparing(BookResponse::getPrice))
                .toList();


        if (filteredByPrice.isEmpty()) throw new BookNotFoundException("No book found.");

        return filteredByPrice;
    }

    public Book save (Book book) {
        return bookRepository.save(book);
    }

    public void replace (Book book) {
        Book foundBook = findById(book.getId());

        if (book.getName() == null) book.setName (foundBook.getName());
        if (book.getPrice() == null) book.setPrice (foundBook.getPrice());
        if (book.getAuthorName() == null) book.setAuthorName (foundBook.getAuthorName());
        if (book.getReleaseYear() == null) book.setReleaseYear (foundBook.getReleaseYear());

        bookRepository.save(book);
    }

    public void deleteById (Long id) {
        if (id == null || id <= 0) throw new InvalidRequestValuesException ("Id cannot be null, equals or lower than 0");

        findById(id);

        bookRepository.deleteById(id);
    }
}
