package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by sergei on 21/02/2025
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return List.of();
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<Author> findAuthorListByLastNameLike(String lastName) {
        return List.of();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }
}
