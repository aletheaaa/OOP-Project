package is442.portfolioAnalyzer.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import is442.portfolioAnalyzer.Exception.*;
import is442.portfolioAnalyzer.config.*;
import org.springframework.http.MediaType;


import javax.validation.Valid;
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

    @PostMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userServiceImpl.changePassword(request.getEmail(), request.getCurrentPassword(), request.getNewPassword());

        String message = "Password changed successfully";
        return ResponseEntity.ok(message);
    }

}

