package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by sergei on 18/02/2025
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("guru.springframework.jdbc.dao")
public class BookDaoIntegrationTest {
    @Autowired
    BookDao bookDao;

    @Test
    public void testGetBookById() {

        Book book = bookDao.getById(5L);

        assertThat(book).isNotNull();
    }

    @Test
    public void testGetAll() {

        List<Book> books = bookDao.findAll();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    public void testGetBookByTitle() {

        Book book = bookDao.findBookByTitle("Spring in Action. 5th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    public void testGetBookByTitle_whenThereIsNoBooksWithNoTitle() {

        assertThrows(EntityNotFoundException.class, ()->bookDao.findBookByTitle(null));
    }

    @Test
    public void testSave() {

        Book book = new Book();
        book.setIsbn("an ISBN");
        book.setTitle("a Title");

        Book saved = bookDao.save(book);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo(book.getTitle());

    }

    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setTitle("j-thomson's book");
        book.setIsbn("some-new-ISBN");

        Book persisted = bookDao.save(book);
        persisted.setTitle("John's book");
        persisted.setIsbn("another-ISBN");

        Book updated = bookDao.update(persisted);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(persisted.getId());
        assertThat(updated.getTitle()).isEqualTo("John's book");
        assertThat(updated.getIsbn()).isEqualTo("another-ISBN");

    }

    @Test
    public void testDeleteById() {

        Book book = new Book();
        book.setTitle("Book to delete");
        book.setIsbn("ISBN");

        Book saved = bookDao.save(book);

        bookDao.deleteById(saved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, ()-> bookDao.getById(saved.getId()));
    }
}
