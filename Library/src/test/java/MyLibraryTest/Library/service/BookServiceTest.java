package MyLibraryTest.Library.service;

import MyLibraryTest.Library.entity.Book;
import MyLibraryTest.Library.exceptions.BookNotFoundException;
import MyLibraryTest.Library.mapper.BookMapper;
import MyLibraryTest.Library.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    public void shouldReturnBook_IfExist () {
        Book book = Book.builder().name("Book example 1").build();

        when (bookRepository.findByNameIgnoreCase("Book example 1")).thenReturn(Optional.of(book));

        Book byName = bookService.findByName("Book example 1");

        verify(bookRepository).findByNameIgnoreCase("Book example 1");

        Assertions.assertEquals("Book example 1", byName.getName());
    }

    @Test
    public void shouldSaveBook () {
        Book entity = Book.builder().name("Book example 1").build();
        Book saved = Book.builder().id(1L).name("Book example 1").build();

        when (bookRepository.save(any(Book.class))).thenReturn(saved);

        Book result = bookService.save(entity);

        Assertions.assertEquals(result.getId(), saved.getId());

        verify (bookRepository).save(entity);
    }

    @Test
    public void shouldDeleteBook_IfExist () {
        Book entity = Book.builder().id(6L).name("Book example 1").build();

        when (bookRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        bookService.deleteById(entity.getId());

        verify(bookRepository, times(1)).deleteById(entity.getId());
    }

    @Test
    public void shouldThrowBookNotFoundException_WhenTryDeleteNonExistentBook () {
        when (bookRepository.findById(1000L)).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.findById(1000L));
    }
}