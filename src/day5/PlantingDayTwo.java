package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PlantingDayTwo {

    private static final String PATH_TO_INPUT = "src/day5/input";

    private static final Logger logger = Logger.getLogger(PlantingDayTwo.class.getName());
    private static final Pattern SEEDS_PATTERN = Pattern.compile(":\\s+");
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

    private PlantingDayTwo() {

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

            long[] starts = new long[seedsStrings.length/2];
            long[] ranges = new long[seedsStrings.length/2];

            for(int i = 0; i < seedsStrings.length; i+=2) {
                starts[i/2] = Long.parseLong(seedsStrings[i]);
                ranges[i/2] = Long.parseLong(seedsStrings[i+1]);
            }

            MyMap[] maps = new MyMap[7];

            line = bufferedReader.readLine();

            for(int i = 0; i < 7; i++) {

                while(line != null && line.isBlank()) {
                    line = bufferedReader.readLine();
                }

                if(line == null) {
                    throw new IllegalArgumentException("Wrong input file");
                }

                List<String> strings = new ArrayList<>(10);
                line = bufferedReader.readLine();

                while(line != null && !line.isBlank()) {
                    strings.add(line);
                    line = bufferedReader.readLine();
                }

                maps[i] = stringsToMap(strings);
            }

            for(long location = 0L; location < Long.MAX_VALUE; location++) {
                long seed = mapLocationToSeed(location, maps);
                if(checkIfCorrectSeed(seed, starts, ranges)) {
                    return location;
                }
            }

            return -1L;

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

    private static long mapLocationToSeed(long location, MyMap[] maps) {
        long destination = location;

        for(int i = maps.length-1; i >= 0; i--) {
            destination = maps[i].mapDestinationToSource(destination);
        }

        return destination;
    }

    private static boolean checkIfCorrectSeed(long seed, long[] starts, long[] ranges) {
        for(int i = 0; i < starts.length; i++) {
            if(seed >= starts[i] && seed < starts[i] + ranges[i]) {
                return true;
            }
        }
        return false;
    }
}
