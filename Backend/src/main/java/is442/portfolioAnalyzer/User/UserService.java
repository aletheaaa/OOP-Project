package is442.portfolioAnalyzer.User;

import is442.portfolioAnalyzer.Exception.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is442.portfolioAnalyzer.User.UserDTO;
import is442.portfolioAnalyzer.config.*;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
    UserDetails getUserByEmail(String email) throws UserNotFoundException;

}


