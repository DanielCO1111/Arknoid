package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

/**
 * The arkanoid.HitNotifier interface represents objects that can manage hit event listeners.
 * Implementing classes can add or remove listeners that should be notified of hit events.
 */
public interface HitNotifier {
    /**
     * Adds a arkanoid.HitListener to the list of listeners.
     *
     * @param hl the arkanoid.HitListener to add
     */
    // Add hl as a listener to hit events.
    void addHitListener(HitListener hl);

    /**
     * Removes a arkanoid.HitListener from the list of listeners.
     *
     * @param hl the arkanoid.HitListener to remove
     */
    // Remove hl from the list of listeners to hit events.
    void removeHitListener(HitListener hl);
}