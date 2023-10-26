package is442.portfolioAnalyzer.User;

import java.util.Optional;

import is442.portfolioAnalyzer.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is442.portfolioAnalyzer.User.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {


    private UserDTO userRepository;
    private UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDTO userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by email"));
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by ID: " + id));
    }

    public boolean isPasswordValid(String password) throws InvalidPasswordException {

        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,25}$")) {
//            throw new InvalidPasswordException("Password must meet the specified criteria.");
            return false;
        }
        return true;
    }

    public String changePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            try {
                isPasswordValid(newPassword);
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);

                return "Password changed successfully.";

            } catch (InvalidPasswordException e) {

                return "Password change failed: " + e.getMessage();

            } finally {
                System.out.println("Checking if it jumps to finally");
            }

        } else {
            throw new UserNotFoundException("User not found");
        }
    }



}





