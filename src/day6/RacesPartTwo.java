package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RacesPartTwo {

    private static final String PATH_TO_INPUT = "src/day6/input";

    private static final Logger logger = Logger.getLogger(RacesPartTwo.class.getName());
    private static final Pattern SPLIT_NUMBERS_FROM_TEXT = Pattern.compile(":\\s+");
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

    private RacesPartTwo() {

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
            long time = getNumber(stringNumbers);


            line = bufferedReader.readLine();
            stringNumbers = SPLIT_NUMBERS_FROM_TEXT.split(line)[1].trim();
            long distance = getNumber(stringNumbers);

            return getMargin(time, distance);

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1L;
        }

    }

    private static long getMargin(long time, long distance) {

        double delta = Math.sqrt((double) (time * time - 4L * distance));
        double t1 = ((double) -time - delta) / -2.0;
        double t2 = ((double) -time + delta) / -2.0;

        if(t1 % 1.0 == 0.0) {
            t1 -= 0.1;
        }

        if(t2 % 1.0 == 0.0) {
            t2 += 0.1;
        }

        long max = (long) Math.floor(t1);
        long min = (long) Math.ceil(t2);

        return max - min + 1L;
    }

    private static long getNumber(String line) {
        String[] stringNumbers = SPACE_PATTERN.split(line);

        StringBuilder stringBuilder = new StringBuilder();

        for(String stringNumber: stringNumbers) {
            stringBuilder.append(stringNumber);
        }

        return Long.parseLong(stringBuilder.toString());
    }
}
