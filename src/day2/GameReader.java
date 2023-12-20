package day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public final class GameReader {

    private static final String PATH_TO_INPUT = "src/day2/input";

    private static final Logger logger = Logger.getLogger(GameReader.class.getName());

    private GameReader() {
    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private static int solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            int index = 1;
            int sum = 0;

            while(line != null) {
                line = line.split(":")[1].strip();
                if(correct(line)) {
                    sum += index;
                }
                index++;
                line = bufferedReader.readLine();
            }

            return sum;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }

    private static boolean correct(String s) {
        String[] sets = Arrays.stream(s.split(";"))
                .map(String::strip)
                .toArray(String[]::new);

        for(String set: sets) {
            String[] split = Arrays.stream(set.split(","))
                    .map(String::strip)
                    .toArray(String[]::new);
            for(String cube: split) {
                String[] pair = cube.split(" ");
                if(!correctPair(pair)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean correctPair(String[] s) {
        int count = Integer.parseInt(s[0]);

        return switch(s[1]) {
            case "red" -> count <= 12;
            case "green" -> count <= 13;
            case "blue" -> count <= 14;
            default -> false;
        };
    }
}
