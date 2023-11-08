package is442.portfolioAnalyzer.filter;

import org.springframework.stereotype.Service;
import java.util.concurrent.CopyOnWriteArrayList;
import is442.portfolioAnalyzer.User.*;
import is442.portfolioAnalyzer.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class AccessLogService {

    private final UserDAO userRepository;

    private final List<String> accessLogList = new CopyOnWriteArrayList<>();

    @Autowired
    public AccessLogService(UserDAO userRepository){
        this.userRepository = userRepository;
    }


    public List<String> getAccessLogList(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role userRole = user.getRole();


            if (userRole == Role.ADMIN) {

                return accessLogList; // Return logs for admin
            } else {

                throw new AccessDeniedException("Access to access logs is restricted to admin users");
            }
        } else {
            throw new UserNotFoundException("User not found for email: " + email);
        }
    }

    public void addToAccessLog(String logEntry) {
        accessLogList.add(logEntry);
    }
}