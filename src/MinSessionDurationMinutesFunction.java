import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;

public class MinSessionDurationMinutesFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> sessions) {
        long minMinutes = calculateDurationInMinutes(sessions);
        return new SleepAnalysisResult("Минимальная продолжительность сессии в минутах", minMinutes);
    }

    public long calculateDurationInMinutes(TreeMap<LocalDateTime, SleepingSession> sessions){
        return sessions.values().stream()
                .mapToLong(session -> Duration.between(session.getStart(), session.getEnd()).toMinutes())
                .min()
                .orElse(0);
    }
}
