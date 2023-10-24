package is442.portfolioAnalyzer.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import is442.portfolioAnalyzer.Exception.*;
import is442.portfolioAnalyzer.config.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userServiceImpl.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userServiceImpl.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    @PostMapping("/id/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        String message = userServiceImpl.changePassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok(message);
    }

}

