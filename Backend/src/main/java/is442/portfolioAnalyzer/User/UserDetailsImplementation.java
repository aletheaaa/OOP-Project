package is442.portfolioAnalyzer.User;

import is442.portfolioAnalyzer.Exception.*;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserDetailsImplementation {
    UserDetails getUserByEmail(String email) throws UserNotFoundException;
    UserDetails getUserById(Integer id) throws UserNotFoundException;
    String changePassword(String email,  String currentPassword,  String newPassword);
    boolean isPasswordValid(String password);
    boolean isCurrentPasswordValid(String password, String currentPassword);
}


