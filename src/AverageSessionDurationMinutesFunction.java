import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;

public class AverageSessionDurationMinutesFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> sessions) {
        long totalMinutes = calculateAverageDurationInMinutes(sessions);
        return new SleepAnalysisResult("Средняя продолжительность сессии", totalMinutes);
    }

    public long calculateAverageDurationInMinutes(TreeMap<LocalDateTime, SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return 0;
        }
        long totalMinutes = sessions.values().stream()
                .mapToLong(session -> Duration.between(session.getStart(), session.getEnd()).toMinutes())
                .sum();
        return totalMinutes / sessions.size();
    }
}
