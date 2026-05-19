import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;

public class MaxSessionDurationMinutesFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> sessions) {
        long maxMinutes = calculateDurationInMinutes(sessions);
        return new SleepAnalysisResult("Максимальная продолжительность сессии в минутах", maxMinutes);
    }

    public long calculateDurationInMinutes(TreeMap<LocalDateTime, SleepingSession> sessions){
        return sessions.values().stream()
                .mapToLong(session -> Duration.between(session.getStart(), session.getEnd()).toMinutes())
                .max()
                .orElse(0);
    }
}
