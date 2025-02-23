package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by sergei on 18/02/2025
 */
public interface BookDao {

    Book getById(Long id);

    List<Book> findAll();

    Book findBookByIsbn(String isbn);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Book getBookByTitle(String title);

    Book readBookByTitle(@Nullable String title);

    Stream<Book> findAllByTitleNotNull();
}
