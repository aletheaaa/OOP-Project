package is442.portfolioAnalyzer.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/sendPasswordResetLink")
    public ResponseEntity<?> sendPasswordResetLink(
            @RequestBody String email) {
        email = email.replace("%40", "@");
        email = email.replace("=", "");
        boolean response = service.sendPasswordResetLink(email);
        if (!response) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        return ResponseEntity.ok().body("Email sent!");

    }

    // Does not work yet
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody String email, @RequestBody String token) {
        System.out.println(email);
        System.out.println(token);
        if (!service.validateEmailWithToken(email, token)) {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
        return ResponseEntity.ok("AuthToken Validated");

    }
}
