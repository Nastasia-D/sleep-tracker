import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;

public class SleepSessionCountFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> count) {
        int totalCount = analyzeSessionCount(count);
        return new SleepAnalysisResult("Общее количество сессий сна", totalCount);
    }
    public int analyzeSessionCount(TreeMap<LocalDateTime, SleepingSession> count) {

        return count.size();
    }
}

