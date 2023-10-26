package is442.portfolioAnalyzer.filter;

import is442.portfolioAnalyzer.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    // @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<String>> getAccessLogs() {
        List<String> accessLogList = accessLogService.getAccessLogList();
        return new ResponseEntity<>(accessLogList, HttpStatus.OK);
    }
}

