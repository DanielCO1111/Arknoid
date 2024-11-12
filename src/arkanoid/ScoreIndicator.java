package arkanoid;

// Daniel Cohen 209313311
// Yona Dassa 211950340

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The arkanoid.ScoreIndicator class represents the score indicator displayed on the screen.
 * It implements the arkanoid.Sprite interface.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    /**
     * Constructs a arkanoid.ScoreIndicator with the specified score counter.
     *
     * @param score the counter representing the score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Draws the score indicator on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        String scoreText = "Score: " + this.score.getValue();
        d.setColor(Color.BLACK);
        d.drawText(SCREEN_WIDTH / 2, 20, scoreText, 20); // Display the score at the top of the screen
    }

    /**
     * Notifies the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        // No need to implement this method for the score indicator
    }

    /**
     * Adds the score indicator to the game.
     *
     * @param g the game to add the score indicator to
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}

