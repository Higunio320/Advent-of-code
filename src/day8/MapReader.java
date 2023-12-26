package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class MapReader {

    private static final String PATH_TO_INPUT = "src/day8/input";

    private static final Logger logger = Logger.getLogger(MapReader.class.getName());

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private MapReader() {

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

            while(line != null) {
                String[] values = line.split(" = ");
                String field = values[0];
                String[] nextDirections = values[1].substring(1, values[1].length() - 1).split(", ");
                map.put(field, new Directions(nextDirections[0], nextDirections[1]));
                line = bufferedReader.readLine();
            }

            int counter = 0;
            int steps = 0;
            String first = "AAA";

            while(!first.equals("ZZZ")) {
                if(directions.charAt(counter) == 'L') {
                    first = map.get(first).goLeft();
                } else {
                    first = map.get(first).goRight();
                }
                counter = (counter + 1) % directions.length();
                steps++;
            }

            return steps;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }
}
