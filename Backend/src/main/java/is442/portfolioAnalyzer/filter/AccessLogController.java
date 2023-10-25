package is442.portfolioAnalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/logs")
public class AccessLogController {

    @GetMapping("/access")
    public String getAccessLogs() {
        try {
            Process process = Runtime.getRuntime().exec("tail -n 100 /path/to/access.log");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder logContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append("\n");
            }
            return logContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading access logs";
        }
    }
}
