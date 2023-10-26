package is442.portfolioAnalyzer.filter;

import org.springframework.stereotype.Service;


import is442.portfolioAnalyzer.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessLogService {
    private final List<String> accessLogList = new ArrayList<>();

    public List<String> getAccessLogList() {
        return accessLogList;
    }

    public void addToAccessLog(String logEntry) {
        accessLogList.add(logEntry);
    }
}