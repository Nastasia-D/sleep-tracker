import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.function.Function;

public class SleepChronotypeFunction implements Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(TreeMap<LocalDateTime, SleepingSession> sessions) {
        String chronotypeValue = determineChronotype(sessions);
        return new SleepAnalysisResult("Хронотип пользователя", chronotypeValue);
    }

    public String determineChronotype(TreeMap<LocalDateTime, SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return "Не определен";
        }
        long larkCount = sessions.values().stream()
                .filter(session -> isLarkSession(session))
                .count();

        long owlCount = sessions.values().stream()
                .filter(session -> isOwlSession(session))
                .count();

        long totalSessions = sessions.size();
        long doveCount = totalSessions - larkCount - owlCount;

        String dominantChronotype;
        if (larkCount > owlCount && larkCount > doveCount) {
            dominantChronotype = "Жаворонок";
        } else if (owlCount > larkCount && owlCount > doveCount) {
            dominantChronotype = "Сова";
        } else {
            dominantChronotype = "Голубь";
        }

        return dominantChronotype;
    }

    private boolean isLarkSession(SleepingSession session) {
        int startHour = session.getStart().getHour();
        int endHour = session.getEnd().getHour();

        boolean isDifferentNight = !session.getStart().toLocalDate().equals(session.getEnd().toLocalDate());

        if (startHour > 5 && startHour < 18) {
            return false;
        }

        return isDifferentNight && (endHour < 7 || startHour < 22);
    }

    private boolean isOwlSession(SleepingSession session) {
        int startHour = session.getStart().getHour();
        int endHour = session.getEnd().getHour();

        boolean isDifferentNight = !session.getStart().toLocalDate().equals(session.getEnd().toLocalDate());

        if (startHour > 5 && startHour < 18) {
            return false;
        }

        boolean lateStart =  !isDifferentNight && (startHour >= 23 || startHour <= 4);
        return lateStart && endHour >= 9;
    }


}

