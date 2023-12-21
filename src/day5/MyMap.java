package day5;

public class MyMap {

    private final long[] sources;
    private final long[] destinations;
    private final long[] ranges;

    public MyMap(long[] sources, long[] destinations, long[] ranges) {
        if(sources.length != destinations.length || sources.length != ranges.length) {
            String message = String.format("Destinations length: %d, sources length: %d, ranges length: %d, must be equal",
                    destinations.length, sources.length, ranges.length);
            throw new IllegalArgumentException(message);
        }

        this.sources = sources;
        this.destinations = destinations;
        this.ranges = ranges;
    }

    public final long map(long source) {
        for(int i = 0; i < sources.length; i++) {
            if(source >= sources[i] && source < sources[i] + ranges[i]) {
                return destinations[i] + source - sources[i];
            }
        }
        return source;
    }

    public final long mapDestinationToSource(long destination) {
        for(int i = 0; i < destinations.length; i++) {
            if(destination >= destinations[i] && destination < destinations[i] + ranges[i]) {
                return sources[i] + destination - destinations[i];
            }
        }
        return destination;
    }
}
