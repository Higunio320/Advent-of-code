package day6;

import day5.PlantingDayTwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Races {

    private static final String PATH_TO_INPUT = "src/day6/input";

    private static final Logger logger = Logger.getLogger(Races.class.getName());
    private static final Pattern SPLIT_NUMBERS_FROM_TEXT = Pattern.compile(":\\s+");
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

    private Races() {

    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private static long solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {

            String line = bufferedReader.readLine();
            String stringNumbers = SPLIT_NUMBERS_FROM_TEXT.split(line)[1].trim();
            long[] times = Arrays.stream(SPACE_PATTERN.split(stringNumbers)).mapToLong(Long::parseLong).toArray();


            line = bufferedReader.readLine();
            stringNumbers = SPLIT_NUMBERS_FROM_TEXT.split(line)[1].trim();
            long[] distances = Arrays.stream(SPACE_PATTERN.split(stringNumbers)).mapToLong(Long::parseLong).toArray();

            return getMargin(times, distances);

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1L;
        }

    }

    private static long getMargin(long[] times, long[] distances) {
        long margin = 1L;

        for(int i = 0; i < times.length; i++) {
            double delta = Math.sqrt((double) (times[i] * times[i] - 4L * distances[i]));
            double t1 = ((double) -times[i] - delta) / -2.0;
            double t2 = ((double) -times[i] + delta) / -2.0;

            if(t1 % 1.0 == 0.0) {
                t1 -= 0.1;
            }

            if(t2 % 1.0 == 0.0) {
                t2 += 0.1;
            }

            long max = (long) Math.floor(t1);
            long min = (long) Math.ceil(t2);
            margin *= (max - min + 1L);
        }
        return margin;
    }
}
