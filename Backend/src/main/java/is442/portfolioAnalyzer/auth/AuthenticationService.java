package is442.portfolioAnalyzer.auth;

import is442.portfolioAnalyzer.User.UserServiceImpl;
import is442.portfolioAnalyzer.config.JwtService;
import is442.portfolioAnalyzer.User.Role;
import is442.portfolioAnalyzer.User.User;
import is442.portfolioAnalyzer.User.UserDTO;
import is442.portfolioAnalyzer.Exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// import java.util.Optional;
// import java.util.NoSuchElementException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    UserServiceImpl userserviceimpl;

    private final UserDTO repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Create the user, save to the database and return generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        User userInfo = userserviceimpl.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(userInfo.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user, else will throw exception
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + request.getEmail()));

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }

    // INCOMPLETE
    public String getTokenByEmail(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));

        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }

    public boolean validateEmailWithToken(String email, String token) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));

        return false;
    }
}
