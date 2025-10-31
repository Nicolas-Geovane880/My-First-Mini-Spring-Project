package MyLibraryTest.Library.repository;

import MyLibraryTest.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {

    public Optional<Book> findByNameIgnoreCase(String name);

    public List<Book> findAllByAuthorNameIgnoreCase(String authorName);
}
