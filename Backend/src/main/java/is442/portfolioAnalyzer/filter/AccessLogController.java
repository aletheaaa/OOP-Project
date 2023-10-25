package is442.portfolioAnalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class AccessLogController {

    private final List<String> accessLogList;

    public AccessLogController(List<String> accessLogList) {
        this.accessLogList = accessLogList;
    }
    @GetMapping("/accessLogs")
    public List<String> getAccessLogs() {
        return accessLogList;
    }
    }

