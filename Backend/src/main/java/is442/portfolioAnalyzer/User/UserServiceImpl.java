package is442.portfolioAnalyzer.user;

import java.util.Optional;

import is442.portfolioAnalyzer.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is442.portfolioAnalyzer.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;


@Service
public class UserServiceImpl implements UserService {

    private UserDTO userRepository;

    @Autowired
    public UserServiceImpl(UserDTO userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

