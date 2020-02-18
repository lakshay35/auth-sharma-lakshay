package sharmalakshay.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sharmalakshay.auth.entity.User;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

  boolean existsByEmail(String email);
}