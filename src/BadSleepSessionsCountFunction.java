import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;

public class BadSleepSessionsCountFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> sessions) {
        long count = countBadSession(sessions);
        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", count);
    }

    public long countBadSession(TreeMap<LocalDateTime, SleepingSession> sessions) {
        return sessions.values().stream()
                .filter(session -> session.getQuality() == SleepAssessment.BAD)
                .count();
    }
}
