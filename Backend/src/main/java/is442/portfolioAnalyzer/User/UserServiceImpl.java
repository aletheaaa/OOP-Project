package is442.portfolioAnalyzer.User;

import java.util.Optional;

import is442.portfolioAnalyzer.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is442.portfolioAnalyzer.User.UserDTO;
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
                .orElseThrow(() -> new UserNotFoundException("User not found by email"));
    }
    public User getUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by ID: " + id));
    }

    public String changePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // encrypt
            user.setPassword(newPassword);
            userRepository.save(user);
            return "Password changed successfully.";
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

