package day8;

public class Directions {

    private final String left;
    private final String right;

    public Directions(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public final String goLeft() {
        return this.left;
    }

    public final String goRight() {
        return this.right;
    }
}
