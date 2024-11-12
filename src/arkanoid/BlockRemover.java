package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import geometry.Velocity;

/**
 * The BlockRemover class is responsible for removing blocks from the game
 * and updating the remaining blocks counter.
 * It listens for hit events on blocks.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;

    /**
     * Constructs a BlockRemover with the specified game and counter.
     *
     * @param game         the game from which blocks will be removed
     * @param removedBlock the counter for the remaining blocks
     */
    public BlockRemover(Game game, Counter removedBlock) {
        this.game = game;
        this.remainingBlocks = removedBlock;
    }

    /**
     * Handles the hit event by removing the block and updating the remaining blocks counter.
     *
     * @param beingHit the block being hit
     * @param hitter   the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isBorder()) {
            // Bounce the ball back
            double dx = hitter.getVelocity().getDx();
            double dy = hitter.getVelocity().getDy();

            // Reverse the appropriate velocity component based on the border hit
            if (beingHit.isTopBorder() || beingHit.isBottomBorder()) {
                dy = -dy;
            } else if (beingHit.isLeftBorder() || beingHit.isRightBorder()) {
                dx = -dx;
            }

            // Set the new velocity for the ball
            hitter.setVelocity(new Velocity(dx, dy));
        } else {
            if (!beingHit.ballColorMatch(hitter)) {
                beingHit.removeFromGame(this.game); // Remove the block from the game
                beingHit.removeHitListener(this); // Remove this listener from the block
                this.remainingBlocks.decrease(1); // Decrease the count of remaining blocks
            }
            if (remainingBlocks.getValue() == 0) {
                // End the game
                game.endGame();
            }
        }
    }
}
