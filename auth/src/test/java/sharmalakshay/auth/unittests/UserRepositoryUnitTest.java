package sharmalakshay.auth.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sharmalakshay.auth.entity.User;
import sharmalakshay.auth.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ComponentScan("sharmalakshay.auth")
@ContextConfiguration(classes = UserRepository.class)
@DataJpaTest
public class UserRepositoryUnitTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private UserRepository repository;

  @Test
  public void whenSaveUser_thenInsertUser() {
    User user = new User("lakshay3@gmail.com", "pasword");
    testEntityManager.persist(user);
    testEntityManager.flush();

    assertEquals(repository.count(), 1);
  }

  @Test
  public void whenFindByEmail_thenReturnUser() {
    User user = new User("lakshay35@gmail.com", "pasword");
    testEntityManager.persist(user);
    testEntityManager.flush();

    User found = repository.findByEmail(user.getEmail());
    assertEquals(found.getEmail(), user.getEmail());
  }

  @Test
  public void whenExistsByEmail_thenReturnTrue() {
    User user = new User("lakshay35@gmail.com", "password");
    testEntityManager.persist(user);

    boolean found = repository.existsByEmail(user.getEmail());
    assertTrue(found);
  }

  @Test
  public void whenDoesNotExistByEmail_thenReturnFalse() {
    User user = new User("lakshay35@gmail.com", "password");
    testEntityManager.persist(user);
    assertEquals(repository.count(), 1);

    boolean found = repository.existsByEmail(user.getEmail() + "s");
    assertFalse(found);
  }
}