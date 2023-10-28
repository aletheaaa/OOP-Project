package is442.portfolioAnalyzer.Token;

import is442.portfolioAnalyzer.Exception.TokenNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    TokenDAO tokenDao;

    public String getValidTokenByUserId(Integer userId) {
        List<Token> tokens = tokenDao.findAllValidTokensByUserId(userId);
        if (tokens.size() == 0) {
            throw new TokenNotValidException("No valid token found for user");
        }
        return tokens.get(0).getToken();
    }

    public void checkTokenBelongsToUser(Integer userId, String token) {
        String userValidToken = getValidTokenByUserId(userId);
        if (!token.equals(userValidToken)) {
            System.out.println("This is the user's valid token: " + userValidToken);
            System.out.println("This is the token: " + token);
            throw new TokenNotValidException("Token does not belong to user");
        }
    }
}
