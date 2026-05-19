import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.TreeMap;


public class SleepTrackerAppTest {

    private TreeMap<LocalDateTime, SleepingSession> testSleep = new TreeMap<>();

    @BeforeEach
    void setUp() {
        testSleep = new TreeMap<>();

        LocalDateTime startTime1 = LocalDateTime.of(2026, 03, 11, 22, 10);
        LocalDateTime endTime1 = LocalDateTime.of(2026, 03, 12, 6, 30);
        SleepingSession session1 = new SleepingSession(startTime1, endTime1, SleepAssessment.NORMAL);

        LocalDateTime startTime2 = LocalDateTime.of(2026, 03, 12, 22, 10);
        LocalDateTime endTime2 = LocalDateTime.of(2026, 03, 13, 7, 20);
        SleepingSession session2 = new SleepingSession(startTime2, endTime2, SleepAssessment.GOOD);

        LocalDateTime startTime3 = LocalDateTime.of(2026, 03, 14, 00, 10);
        LocalDateTime endTime3 = LocalDateTime.of(2026, 03, 14, 6, 10);
        SleepingSession session3 = new SleepingSession(startTime3, endTime3, SleepAssessment.BAD);

        LocalDateTime startTime4 = LocalDateTime.of(2026, 03, 14, 13, 00);
        LocalDateTime endTime4 = LocalDateTime.of(2026, 03, 14, 13, 45);
        SleepingSession session4 = new SleepingSession(startTime4, endTime4, SleepAssessment.BAD);

        LocalDateTime startTime5 = LocalDateTime.of(2026, 03, 15, 01, 10);
        LocalDateTime endTime5 = LocalDateTime.of(2026, 03, 15, 8, 05);
        SleepingSession session5 = new SleepingSession(startTime5, endTime5, SleepAssessment.NORMAL);

        LocalDateTime startTime6 = LocalDateTime.of(2026, 03, 15, 22, 40);
        LocalDateTime endTime6 = LocalDateTime.of(2026, 03, 16, 7, 40);
        SleepingSession session6 = new SleepingSession(startTime6, endTime6, SleepAssessment.GOOD);

        LocalDateTime startTime7 = LocalDateTime.of(2026, 03, 17, 01, 40);
        LocalDateTime endTime7 = LocalDateTime.of(2026, 03, 17, 6, 30);
        SleepingSession session7 = new SleepingSession(startTime7, endTime7, SleepAssessment.BAD);

        LocalDateTime startTime8 = LocalDateTime.of(2026, 03, 17, 12, 40);
        LocalDateTime endTime8 = LocalDateTime.of(2026, 03, 17, 14, 20);
        SleepingSession session8 = new SleepingSession(startTime8, endTime8, SleepAssessment.BAD);


        testSleep.put(startTime1, session1);
        testSleep.put(startTime2, session2);
        testSleep.put(startTime3, session3);
        testSleep.put(startTime4, session4);
        testSleep.put(startTime5, session5);
        testSleep.put(startTime6, session6);
        testSleep.put(startTime7, session7);
        testSleep.put(startTime8, session8);
    }

    @Test
    void testAnalyzeSessionCount() {
        SleepSessionCountFunction function = new SleepSessionCountFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Общее количество сессий сна: 8";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void negativeTestAnalyzeSessionCount() {
        testSleep.clear();
        SleepSessionCountFunction function = new SleepSessionCountFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Общее количество сессий сна: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCalculateAverageDurationInMinutes() {
        AverageSessionDurationMinutesFunction function = new AverageSessionDurationMinutesFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Средняя продолжительность сессии: 350";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void negativeTestCalculateAverageDurationInMinutes() {
        testSleep.clear();
        AverageSessionDurationMinutesFunction function = new AverageSessionDurationMinutesFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Средняя продолжительность сессии: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCalculateDurationInMinutesMin() {
        MinSessionDurationMinutesFunction function = new MinSessionDurationMinutesFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Минимальная продолжительность сессии в минутах: 45";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void negativeTestCalculateDurationInMinutesMin() {
        testSleep.clear();
        MinSessionDurationMinutesFunction function = new MinSessionDurationMinutesFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Минимальная продолжительность сессии в минутах: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCalculateDurationInMinutesMax(){
        MaxSessionDurationMinutesFunction function = new MaxSessionDurationMinutesFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Максимальная продолжительность сессии в минутах: 550";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void negativeTestCalculateDurationInMinutesMax(){
        testSleep.clear();
        MaxSessionDurationMinutesFunction function = new MaxSessionDurationMinutesFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Максимальная продолжительность сессии в минутах: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testDetermineChronotype(){
        SleepChronotypeFunction function = new SleepChronotypeFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Хронотип пользователя: Голубь";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void negativeTestDetermineChronotype(){
        testSleep.clear();
        SleepChronotypeFunction function = new SleepChronotypeFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Хронотип пользователя: Не определен";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCountBadSession(){
        BadSleepSessionsCountFunction function = new BadSleepSessionsCountFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Количество сессий с плохим качеством сна: 4";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void negativeTestCountBadSession(){
        testSleep.clear();
        BadSleepSessionsCountFunction function = new BadSleepSessionsCountFunction();
        SleepAnalysisResult result = function.apply(testSleep);
        String expectedText = "Количество сессий с плохим качеством сна: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCountSleeplessNightsPositive(){
        TreeMap<LocalDateTime, SleepingSession> localTestSession = new TreeMap<>();

        LocalDateTime start1 = LocalDateTime.of(2026, 04,21, 22, 00);
        LocalDateTime end1 = LocalDateTime.of(2026, 04, 22, 7, 30);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepAssessment.NORMAL);

        LocalDateTime start2 = LocalDateTime.of(2026, 04,23, 4, 30);
        LocalDateTime end2 = LocalDateTime.of(2026, 04, 23, 6, 30);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepAssessment.BAD);

        LocalDateTime start3 = LocalDateTime.of(2026, 04,24, 8, 00);
        LocalDateTime end3 = LocalDateTime.of(2026, 04, 24, 9, 30);
        SleepingSession session3 = new SleepingSession(start3, end3, SleepAssessment.BAD);

        localTestSession.put(start1, session1);
        localTestSession.put(start2, session2);
        localTestSession.put(start3, session3);

        SleeplessNightsCountFunction function = new SleeplessNightsCountFunction();
        SleepAnalysisResult result = function.apply(localTestSession);
        String expectedText = "Количество бессонных ночей: 1";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCountSleeplessNightsZero(){
        TreeMap<LocalDateTime, SleepingSession> localTestSession = new TreeMap<>();

        LocalDateTime start1 = LocalDateTime.of(2026, 04,25, 22, 00);
        LocalDateTime end1 = LocalDateTime.of(2026, 04, 26, 7, 30);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepAssessment.NORMAL);

        LocalDateTime start2 = LocalDateTime.of(2026, 04,26, 23, 30);
        LocalDateTime end2 = LocalDateTime.of(2026, 04, 27, 6, 30);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepAssessment.GOOD);

        LocalDateTime start3 = LocalDateTime.of(2026, 04,27, 8, 00);
        LocalDateTime end3 = LocalDateTime.of(2026, 04, 27, 9, 30);
        SleepingSession session3 = new SleepingSession(start3, end3, SleepAssessment.BAD);

        localTestSession.put(start1, session1);
        localTestSession.put(start2, session2);
        localTestSession.put(start3, session3);

        SleeplessNightsCountFunction function = new SleeplessNightsCountFunction();
        SleepAnalysisResult result = function.apply(localTestSession);
        String expectedText = "Количество бессонных ночей: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCountSleeplessNightsEmpty(){
        TreeMap<LocalDateTime, SleepingSession> localTestSession = new TreeMap<>();

        SleeplessNightsCountFunction function = new SleeplessNightsCountFunction();
        SleepAnalysisResult result = function.apply(localTestSession);
        String expectedText = "Количество бессонных ночей: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

    @Test
    void testCountSleeplessNightsDaySleep(){
        TreeMap<LocalDateTime, SleepingSession> localTestSession = new TreeMap<>();

        LocalDateTime start1 = LocalDateTime.of(2026, 04,25, 12, 00);
        LocalDateTime end1 = LocalDateTime.of(2026, 04, 26, 15, 30);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepAssessment.NORMAL);

        LocalDateTime start2 = LocalDateTime.of(2026, 04,26, 11, 30);
        LocalDateTime end2 = LocalDateTime.of(2026, 04, 27, 12, 30);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepAssessment.GOOD);

        LocalDateTime start3 = LocalDateTime.of(2026, 04,27, 8, 00);
        LocalDateTime end3 = LocalDateTime.of(2026, 04, 27, 9, 30);
        SleepingSession session3 = new SleepingSession(start3, end3, SleepAssessment.BAD);

        localTestSession.put(start1, session1);
        localTestSession.put(start2, session2);
        localTestSession.put(start3, session3);

        SleeplessNightsCountFunction function = new SleeplessNightsCountFunction();
        SleepAnalysisResult result = function.apply(localTestSession);
        String expectedText = "Количество бессонных ночей: 0";
        Assertions.assertEquals(expectedText, result.toString());
    }

}
// Финальная версия проекта с тестами
