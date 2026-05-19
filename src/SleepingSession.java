import java.time.LocalDateTime;

public class SleepingSession {

    private LocalDateTime start;
    private LocalDateTime end;
    private SleepAssessment quality;

    public SleepingSession(LocalDateTime start, LocalDateTime end, SleepAssessment quality){
        this.start = start;
        this.end = end;
        this.quality = quality;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepAssessment getQuality() {
        return quality;
    }
}
