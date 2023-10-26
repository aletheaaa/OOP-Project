package is442.portfolioAnalyzer.auth;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.NoSuchElementException;

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

        Optional<User> existingUser = repository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {

            User user = userserviceimpl.getUserByEmail(request.getEmail());
            var jwtToken = jwtService.generateToken(user);


            throw new UserAlreadyExistsException("User already exists");
        }

        if (!userserviceimpl.isPasswordValid(request.getPassword())){

            throw new InvalidPasswordException("Invalid Password");
        } else {

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
                    .status("200")
                    .build();
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user, else will throw exception
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + request.getEmail()));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .status("400")
                .build();
    }
}




