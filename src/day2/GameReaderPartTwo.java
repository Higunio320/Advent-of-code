package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

public final class GameReaderPartTwo {

    private static final String PATH_TO_INPUT = "src/day2/input";

    private static final Logger logger = Logger.getLogger(GameReaderPartTwo.class.getName());

    private GameReaderPartTwo() {
    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    public static int solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {

            String line = bufferedReader.readLine();
            int sum = 0;

            while(line != null) {
                line = line.split(":")[1].strip();
                sum += power(line);
                line = bufferedReader.readLine();
            }

            return sum;

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }

    private static int power(String s) {
        String[] sets = Arrays.stream(s.split(";"))
                .map(String::strip)
                .toArray(String[]::new);

        int[] powers = new int[3];

        for(String set: sets) {
            String[] split = Arrays.stream(set.split(","))
                    .map(String::strip)
                    .toArray(String[]::new);
            for(String cube: split) {
                String[] pair = cube.split(" ");
                int count = Integer.parseInt(pair[0]);
                switch(pair[1]) {
                    case "red" -> powers[0] = Math.max(powers[0], count);
                    case "green" -> powers[1] = Math.max(powers[1], count);
                    case "blue" -> powers[2] = Math.max(powers[2], count);
                    default -> {
                        String warning = String.format("Invalid color: %s", pair[1]);
                        logger.warning(warning);
                    }
                }
            }
        }

        return powers[0] * powers[1] * powers[2];
    }
}
