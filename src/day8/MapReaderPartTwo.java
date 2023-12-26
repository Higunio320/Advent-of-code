package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MapReaderPartTwo {

    private static final String PATH_TO_INPUT = "src/day8/input";

    private static final Logger logger = Logger.getLogger(MapReaderPartTwo.class.getName());

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private MapReaderPartTwo() {

    }

    private static long solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {

            String directions = bufferedReader.readLine();

            String line = bufferedReader.readLine();

            while(line.isBlank()) {
                line = bufferedReader.readLine();
            }

            Map<String, Directions> map = new HashMap<>(10);
            List<String> starts = new ArrayList<>(10);

            while(line != null) {
                String[] values = line.split(" = ");
                String field = values[0];
                if(field.charAt(2) == 'A') {
                    starts.add(field);
                }
                String[] nextDirections = values[1].substring(1, values[1].length() - 1).split(", ");
                map.put(field, new Directions(nextDirections[0], nextDirections[1]));
                line = bufferedReader.readLine();
            }

            String[] first = starts.toArray(new String[0]);

            List<Long> stepsNeeded = new ArrayList<>(10);

            for(String field: first) {
                stepsNeeded.add(stepsNeededToEnd(map, directions, field));
            }

            return findLCM(stepsNeeded);    // every route loops with the same amount of steps as it's needed to get to **Z from the start,
                                            // so all we need is to find LCM of all steps from the start to **Z
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }

    private static long stepsNeededToEnd(Map<String, Directions> map, String directions, String start) {
        String first = start;
        int counter = 0;
        long steps = 0;

        while (first.charAt(2) != 'Z' || steps == 0) {
            if (directions.charAt(counter) == 'L') {
                first = map.get(first).goLeft();
            } else {
                first = map.get(first).goRight();
            }
            counter = (counter + 1) % directions.length();
            steps++;
        }

        return steps;
    }

    private static long findLCM(List<Long> numbers) {
        long lcm = 1;

        for(Long number : numbers) {
            lcm = findLCM(lcm, number);
        }

        return lcm;
    }

    private static long findLCM(long a, long b) {
        return a * b / findGCD(a, b);
    }

    private static long findGCD(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
