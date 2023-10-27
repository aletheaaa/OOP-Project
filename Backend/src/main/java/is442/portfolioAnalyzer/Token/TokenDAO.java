package is442.portfolioAnalyzer.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenDAO extends JpaRepository<Token, Integer> {

    @Query("""
            SELECT t from Token t inner join User u on t.user.id = u.id
            WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findALlValidTokensByUserId(Integer userId);

    Optional<Token> findByToken(String token);
}
