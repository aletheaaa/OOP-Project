package is442.portfolioAnalyzer.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDTO extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

