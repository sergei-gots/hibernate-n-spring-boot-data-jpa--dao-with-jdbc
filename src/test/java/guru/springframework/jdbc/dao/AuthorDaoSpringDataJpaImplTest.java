package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by sergei on 26/02/2025
 */
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("guru.springframefork.jdbc.dao")
public class AuthorDaoSpringDataJpaImplTest {

    @Autowired
    AuthorRepository authorRepository;

    AuthorDaoSpringDataJpaImpl authorDao;

    @BeforeEach
    public void setUp() {
        authorDao = new AuthorDaoSpringDataJpaImpl(authorRepository);
    }

    @Test
    public void testGetAll() {

        List<Author> authorList = authorDao.findAll(Pageable.ofSize(10));

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isGreaterThan(3);
    }

    @Test
    public void TestGetAuthorsByLastName_SortByFirstName() {

        int pageSize = 10;


        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");

        Pageable pageable = PageRequest.of(0, pageSize, sort);

        List<Author> authorList = authorDao.findAllByLastName("Smith", pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);
    }

    @Test
    public void TestReadAuthorsByLastName() {

        int pageSize = 10;


        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");

        Pageable pageable = PageRequest.of(0, pageSize, sort);

        Page<Author> authorPage = authorDao.readAllByLastName("Smith", pageable);

        assertThat(authorPage).isNotNull();
        assertThat(authorPage.getContent().size()).isEqualTo(pageSize);
    }


    @Test
    public void TestReadAuthorsByLastNameLike() {

        int pageSize = 10;


        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");

        Pageable pageable = PageRequest.of(0, pageSize, sort);

        Page<Author> authorPage = authorDao.readAllByLastNameLike("%mit%", pageable);

        assertThat(authorPage).isNotNull();
        assertThat(authorPage.getContent().size()).isEqualTo(pageSize);
    }

    @Test
    public void TestGetAuthorsByLastName_SortByFirstName_DefaultIsAsc_Page1() {

        int pageSize = 10;

        Sort sortByFirstName = Sort.by("firstName");
        Pageable pageable = PageRequest.of(0, pageSize, sortByFirstName);

        List<Author> authorList = authorDao.findAllByLastName("Smith", pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);

        assertThat(authorList.get(0).getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    public void TestGetAuthorsByLastNameLike_SortByFirstName_DefaultIsAsc_Page1() {

        int pageSize = 10;

        Sort sortByFirstName = Sort.by("firstName");
        Pageable pageable = PageRequest.of(0, pageSize, sortByFirstName);

        List<Author> authorList = authorDao.findAllByLastNameLike("%mit%", pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);

        assertThat(authorList.get(0).getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    public void TestGetAuthorsByLastName_SortByFirstName_Page2() {

        int pageSize = 10;

        Sort sortByFirstName = Sort.by("firstName");
        Pageable pageable = PageRequest.of(1, pageSize, sortByFirstName);

        List<Author> authorList = authorDao.findAllByLastName("Smith", pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);

        assertThat(authorList.get(0).getFirstName()).isEqualTo("Dinesh");
    }

    @Test
    public void TestGetAuthorsByLastName_SortByFirstName_Page1_Desc() {

        int pageSize = 10;

        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");

        Pageable pageable = PageRequest.of(0, pageSize, sort);

        List<Author> authorList = authorDao.findAllByLastName("Smith", pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);

        assertThat(authorList.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    public void TestGetAuthorsByLastName_SortByFirstName_Desc_Page1_Limit40() {

        int pageSize = 40;

        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");

        Pageable pageable = PageRequest.of(0, pageSize, sort);

        List<Author> authorList = authorDao.findAllByLastName("Smith", pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);

        assertThat(authorList.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    public void TestGetAuthors_Page1_Limit41() {

        int pageSize = 41;

        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");

        Pageable pageable = PageRequest.of(0, pageSize, sort);

        List<Author> authorList = authorDao.findAll(pageable);

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(pageSize);

    }

    @Test
    void saveNewAuthor() {

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");

        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("John");
    }

    @Test
    void updateAuthor() {

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setFirstName("J.");

        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated).isEqualTo(saved);
        assertThat(updated.getFirstName()).isEqualTo("J.");

    }

    @Test
    void deleteById() {

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");

        Author saved = authorDao.saveNewAuthor(author);

        Long id = saved.getId();

        authorDao.deleteById(id);

        assertThrows(EntityNotFoundException.class, ()-> authorDao.getById(id));

    }
}
