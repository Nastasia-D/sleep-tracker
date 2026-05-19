import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.TreeMap;
import java.util.function.Function;

public class SleeplessNightsCountFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

        @Override
        public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> sessions) {
            long count = countSleeplessNights(sessions);
            return new SleepAnalysisResult("Количество бессонных ночей", count);
        }

        public long countSleeplessNights(TreeMap<LocalDateTime, SleepingSession> sessions) {

            if (sessions == null || sessions.isEmpty()) {
                return 0;
            }

            LocalDateTime firstSessionStart = sessions.get(sessions.firstKey()).getStart();
            LocalDate startNightDate;

            if(firstSessionStart.getHour() >= 12){
                startNightDate = firstSessionStart.toLocalDate().plusDays(1);
            } else {
                startNightDate = firstSessionStart.toLocalDate();
            }

            LocalDateTime lastSessionEnd = sessions.get(sessions.lastKey()).getEnd();
            LocalDate endNightDate = lastSessionEnd.toLocalDate().plusDays(1);

            int totalNight = Period.between(startNightDate,endNightDate).getDays();

            long nightsWithSleep = sessions.values().stream()
                    .filter(session -> {
                        LocalDateTime start = session.getStart();
                        LocalDateTime end = session.getEnd();

                        return start.getHour() < 6 || (start.toLocalDate().isBefore(end.toLocalDate()) && end.getHour() > 0);
                    })

                    .map(session -> session.getEnd().toLocalDate())
                    .count();
            return totalNight - nightsWithSleep;
        }
    }

