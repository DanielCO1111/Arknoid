package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

/**
 * The arkanoid.BallRemover class is responsible for removing balls from the game
 * when they hit a death region block.
 * It implements the arkanoid.HitListener interface to respond to hit events.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter remainingBalls;

    /**
     * Constructs a new arkanoid.BallRemover.
     *
     * @param game           The game from which balls will be removed
     * @param remainingBalls A counter tracking the number of remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Responds to a hit event between a block and a ball.
     * If the hit block is a death region, the ball is removed from the game
     * and the remaining balls counter is decreased.
     *
     * @param beingHit The block that was hit
     * @param hitter   The ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isDeathRegion()) {
            hitter.removeFromGame(game);
            remainingBalls.decrease(1);
        }
    }
}
