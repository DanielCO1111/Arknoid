package arkanoid;

// Daniel Cohen 209313311
// Yona Dassa 211950340

/**
 * The arkanoid.ScoreTrackingListener class is responsible for tracking the score in response to hit events.
 * It implements the arkanoid.HitListener interface.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructs a arkanoid.ScoreTrackingListener with the specified score counter.
     *
     * @param scoreCounter the counter representing the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * It increases the score by 5 points for each block hit.
     *
     * @param beingHit the block that is being hit
     * @param hitter   the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5); // Increase score by 5 for every block hit
    }
}

