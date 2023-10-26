package is442.portfolioAnalyzer.filter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/logs")
public class AccessLogController {
    @Autowired
    private AccessLogService accessLogService;



    @Autowired
    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @GetMapping("/accessLogs")

    public ResponseEntity<List<String>> getAccessLogs(@RequestParam String email) {
        List<String> accessLogList = accessLogService.getAccessLogList(email);
        return new ResponseEntity<>(accessLogList, HttpStatus.OK);
    }
}

