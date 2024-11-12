package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

/**
 * The arkanoid.Counter class represents a simple counter that can be incremented and decremented.
 * It holds an integer value that can be modified through its methods.
 */
public class Counter {
    private int count;

    /**
     * Creates a new arkanoid.Counter with an initial count of 0.
     * @param count counting
     */
    public Counter(int count) {
        this.count = count;
    }

    /**
     * Increases the counter by the given number.
     *
     * @param number the number to increase the counter by
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decreases the counter by the given number.
     *
     * @param number the number to decrease the counter by
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Returns the current count value.
     *
     * @return the current count
     */
    public int getValue() {
        return this.count;
    }
}
