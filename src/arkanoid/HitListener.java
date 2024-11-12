package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

/**
 * The arkanoid.HitListener interface represents objects that can be notified of hit events.
 * Implementing classes can define actions to be taken when a block is hit by a ball.
 */
public interface HitListener {

    /**
     * This method is called when a block is hit by a ball.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}


