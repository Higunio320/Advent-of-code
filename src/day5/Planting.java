package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class Planting {

    private static final String PATH_TO_INPUT = "src/day5/input";

    private static final Logger logger = Logger.getLogger(Planting.class.getName());
    private static final Pattern SEEDS_PATTERN = Pattern.compile(":\\s+");
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

    private Planting() {

    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private static long solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {

            String line = SEEDS_PATTERN.split(bufferedReader.readLine())[1];
            String[] seedsStrings = SPACE_PATTERN.split(line);
            long[] seeds = Arrays.stream(seedsStrings).mapToLong(Long::parseLong).toArray();

            MyMap[] maps = new MyMap[7];

            line = bufferedReader.readLine();

            for(int i = 0; i < 7; i++) {

                while(line.isBlank()) {
                    line = bufferedReader.readLine();
                }

                List<String> strings = new ArrayList<>(10);
                line = bufferedReader.readLine();

                while(line != null && !line.isBlank()) {
                    strings.add(line);
                    line = bufferedReader.readLine();
                }

                maps[i] = stringsToMap(strings);
            }

            long min = Long.MAX_VALUE;

            for(long seed: seeds) {
                long result = mapToLocation(seed, maps);
                if(result < min) {
                    min = result;
                }
            }

            return min;

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1L;
        }
    }

    private static MyMap stringsToMap(List<String> strings) {
        long[] sources = new long[strings.size()];
        long[] destinations = new long[strings.size()];
        long[] ranges = new long[strings.size()];

        for(int i = 0; i < strings.size(); i++) {
            String[] numbers = SPACE_PATTERN.split(strings.get(i));
            destinations[i] = Long.parseLong(numbers[0]);
            sources[i] = Long.parseLong(numbers[1]);
            ranges[i] = Long.parseLong(numbers[2]);
        }

        return new MyMap(sources, destinations, ranges);
    }

    private static long mapToLocation(long seed, MyMap[] maps) {
        long source = seed;

        for (MyMap map : maps) {
            source = map.map(source);
        }

        return source;
    }
}
