package is442.portfolioAnalyzer.auth;

import is442.portfolioAnalyzer.Token.Token;
import is442.portfolioAnalyzer.Token.TokenDAO;
import is442.portfolioAnalyzer.Token.TokenType;
import is442.portfolioAnalyzer.User.UserServiceImpl;
import is442.portfolioAnalyzer.config.*;
import is442.portfolioAnalyzer.User.Role;
import is442.portfolioAnalyzer.User.User;
import is442.portfolioAnalyzer.User.UserDTO;
import is442.portfolioAnalyzer.Exception.*;
import is442.portfolioAnalyzer.ExceptionHandler.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    UserServiceImpl userserviceimpl;

    private final UserDTO repository;
    private final TokenDAO tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // REGISTER
    // ----------------------------------------------------------------------------------------
    // Create the user, save to the database and return generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> existingUser = repository.findByEmail(request.getEmail());

        // Check if user already exists
        if (existingUser.isPresent()) {

            User user = userserviceimpl.getUserByEmail(request.getEmail());
            var jwtToken = jwtService.generateToken(user);
            throw new UserAlreadyExistsException("User already exists");
        }

        if (!userserviceimpl.isPasswordValid(request.getPassword())) {

            throw new InvalidPasswordException("Invalid Password");
        } else {

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

    }

    // AUTHENTICATE
    // ----------------------------------------------------------------------------------------
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
    // ----------------------------------------------------------------------------------------
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

    // FORGOT PASSWORD
    // ---------------------------------------------------------------------------------
    public void forgotPassword(String email, String newPassword, String confirmPassword) {

    }

    public boolean sendPasswordResetLink(String email) {
        String token = getTokenForEmail(email);
        Optional<User> existingUser = repository.findByEmail(email);
        // Check if user already exists
        if (existingUser.isPresent()) {
            String url = "http://localhost:3000/forgotPassword/" + token;
            ForgotPasswordEmail.forgotPassword(email, url);
            return true;
        }
        return false;
    }

    public String getTokenForEmail(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));

        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }

    public boolean validateEmailWithToken(String email, String token) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));

        List<Token> tokens = tokenDao.findAllValidTokensByUserId(user.getId());

        for (Token existingToken : tokens) {
            if (existingToken.getToken().equals(token) && !existingToken.isExpired() && !existingToken.isRevoked()
                    && existingToken.getUser().equals(user)) {
                return true;
            }
        }

        return false;
    }
}
