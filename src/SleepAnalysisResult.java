public class SleepAnalysisResult {

    private final Object value;
    private final String description;

    public SleepAnalysisResult(String description, Object value){
        this.description = description;
        this.value = value;
    }

    @Override
    public String toString() {
        return description + ": " + value;
    }
}