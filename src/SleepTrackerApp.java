import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;


public class SleepTrackerApp {

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("Ошибка: не указан путь к файлу с данными");
            return;
        }

        SleepTrackerApp app = new SleepTrackerApp();

        try {

            String filePath = args[0];
            TreeMap<LocalDateTime, SleepingSession> sleepDataTime = app.loadSleepData(filePath,  StandardCharsets.UTF_8);
            List<Function<TreeMap<LocalDateTime, SleepingSession>, SleepAnalysisResult>>  analyzers = new ArrayList<>();

            analyzers.add(new SleepSessionCountFunction());
            analyzers.add(new MinSessionDurationMinutesFunction());
            analyzers.add(new MaxSessionDurationMinutesFunction());
            analyzers.add(new AverageSessionDurationMinutesFunction());
            analyzers.add(new BadSleepSessionsCountFunction());
            analyzers.add(new SleeplessNightsCountFunction());
            analyzers.add(new SleepChronotypeFunction());

            System.out.println("Анализ данных сна: ");

            analyzers.stream()
                    .map(analyzer -> analyzer.apply(sleepDataTime).toString())
                    .forEach(System.out :: println);

        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла " + e.getMessage());
        }




    }

    public TreeMap<LocalDateTime, SleepingSession> loadSleepData(String fileName, Charset charset) throws IOException {
        Path path = Paths.get(fileName);
        try (Stream<String> lines = Files.lines(path, charset)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            return lines
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
                        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
                        SleepAssessment quality = SleepAssessment.valueOf(parts[2]);
                        return new SleepingSession(start, end, quality);
                    })
                    .collect(Collectors.toMap(
                                    SleepingSession::getStart,
                                    item -> item,
                                    (itemExisting, itemReplacement) -> itemExisting,
                                    TreeMap::new

                            )
                    );
        }
// считывание файла и запуск аналитических функций

    }
}

