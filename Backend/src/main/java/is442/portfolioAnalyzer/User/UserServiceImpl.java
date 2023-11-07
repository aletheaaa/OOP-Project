package is442.portfolioAnalyzer.User;

import java.util.Optional;

import is442.portfolioAnalyzer.Exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return false;
        }
        return true;
    }

    public boolean isCurrentPasswordValid(String password, String currentPassword) {
        return passwordEncoder.matches(currentPassword, password);
    }

    public String changePassword(String email, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Verify that the provided current password matches the stored password
            if (!isCurrentPasswordValid(user.getPassword(), currentPassword) || !isPasswordValid(newPassword)) {
                throw new InvalidPasswordException("Invalid Password");
            }

            // Update the user's password with the new hashed password
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            return "Password changed successfully";
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
