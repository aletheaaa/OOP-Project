package is442.portfolioAnalyzer.filter;

import org.springframework.stereotype.Service;
import java.util.concurrent.CopyOnWriteArrayList;

import is442.portfolioAnalyzer.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessLogService {
    private final List<String> accessLogList = new CopyOnWriteArrayList<>();
    public List<String> getAccessLogList() {
        return accessLogList;
    }

    public void addToAccessLog(String logEntry) {
        accessLogList.add(logEntry);
    }
}