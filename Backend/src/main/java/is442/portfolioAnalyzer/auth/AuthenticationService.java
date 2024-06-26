package is442.portfolioAnalyzer.auth;

import is442.portfolioAnalyzer.Token.Token;
import is442.portfolioAnalyzer.Token.TokenDAO;
import is442.portfolioAnalyzer.Token.TokenType;
import is442.portfolioAnalyzer.User.UserService;
import is442.portfolioAnalyzer.config.*;
import is442.portfolioAnalyzer.User.Role;
import is442.portfolioAnalyzer.User.User;
import is442.portfolioAnalyzer.User.UserDAO;
import is442.portfolioAnalyzer.Exception.*;
import is442.portfolioAnalyzer.Tools.RandomStringGenerator;
import is442.portfolioAnalyzer.Tools.Email.SendEmail;

import lombok.RequiredArgsConstructor;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    UserService userserviceimpl;

    private final UserDAO repository;
    private final TokenDAO tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // REGISTER
    // --------------------------------------------------------------------------------
    // Create the user, save to the database and return generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> existingUser = repository.findByEmail(request.getEmail());

        // Check if user already exists
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (!userserviceimpl.isPasswordValid(request.getPassword())) {
            throw new InvalidPasswordException(
                    "Invalid Password! Password must be between 8 and 25 characters " +
                            "and contain at least one uppercase letter, one lowercase letter, " +
                            "one digit and one special character among @, #, $, %, ^, &, +, =, " +
                            "and !");
        }

        // Create the user
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var savedUser = repository.save(user);
        User userInfo = userserviceimpl.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(userInfo.getId())
                .status("200")
                .build();

    }

    // AUTHENTICATE
    // --------------------------------------------------------------------------------
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user, else will throw exception
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + request.getEmail()));

        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .status("200")
                .build();
    }

    // USER TOKENS FUNCTIONALITY
    // --------------------------------------------------------------------------------
    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenDao.findAllValidTokensByUserId(user.getId());
        if (validUserToken.isEmpty()) {
            return;
        }
        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenDao.save(token);
        });
    }

    // Populate the token table with the generated token
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenDao.save(token);
    }

    // RESET PASSWORD
    // --------------------------------------------------------------------------------
    public String resetPassword(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        String newPassword = RandomStringGenerator.generateRandomString();

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Update the user's password with the new hashed password
            user.setPassword(passwordEncoder.encode(newPassword));
            repository.save(user);

            // Send reset password to user
            String emailSubject = "Password Reset Request";
            String emailBody = "New password: " + newPassword;
            SendEmail.sendEmail(email, emailSubject, emailBody);

            return "Password reset successfully!";
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
