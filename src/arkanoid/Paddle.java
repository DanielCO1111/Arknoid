package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import java.awt.Color;
/**
 * The arkanoid.Paddle class represents the paddle controlled by the player.
 * It implements the arkanoid.Sprite and arkanoid.arkanoid.Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddleRect;
    private int paddleSpeed;
    private Color color;
    private int screenWidth;
    private double regionWidth;
    private double borderWidth;
    private static final int NUBERS_OF_REGIONS = 5;

    /**
     * Constructs a arkanoid.Paddle with the specified parameters.
     *
     * @param keyboard    the keyboard sensor to detect user input
     * @param paddleRect  the rectangle representing the paddle's shape and position
     * @param paddleSpeed the speed of the paddle
     * @param color       the color of the paddle
     * @param screenWidth the width of the screen
     * @param borderWidth the width of the borders
     */
    public Paddle(KeyboardSensor keyboard, Rectangle paddleRect, int paddleSpeed,
                  Color color, int screenWidth, int borderWidth) {
        this.keyboard = keyboard;
        this.paddleRect = paddleRect;
        this.paddleSpeed = paddleSpeed;
        this.color = color;
        this.screenWidth = screenWidth;
        this.borderWidth = borderWidth;
    }

    /**
     * Moves the paddle to the left by paddleSpeed units.
     */
    public void moveLeft() {
        if (paddleRect.getUpperLeft().getX() > borderWidth) {
            paddleRect.setUpperLeft(paddleRect.getUpperLeft().getX() - paddleSpeed, paddleRect.getUpperLeft().getY());
        } else {
            paddleRect.setUpperLeft(paddleRect.getUpperLeft().getX() + screenWidth, paddleRect.getUpperLeft().getY());
        }
    }

    /**
     * Moves the paddle to the right by paddleSpeed units.
     */

    public void moveRight() {
        if (paddleRect.getUpperLeft().getX() < screenWidth - borderWidth - paddleRect.getWidth()) {
            paddleRect.setUpperLeft(paddleRect.getUpperLeft().getX()
                    + paddleSpeed, paddleRect.getUpperLeft().getY());
        } else {
            paddleRect.setUpperLeft(paddleRect.getUpperLeft().getX()
                    - screenWidth, paddleRect.getUpperLeft().getY());
        }
    }
    // arkanoid.Sprite

    /**
     * Implements the timePassed method of the arkanoid.Sprite interface.
     * Checks if the left or right keys are pressed and moves
     * the paddle accordingly.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Implements the drawOn method of the arkanoid.Sprite interface.
     * Draws the paddle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle((int) paddleRect.getUpperLeft().getX(),
                (int) paddleRect.getUpperLeft().getY(),
                (int) paddleRect.getWidth(),
                (int) paddleRect.getHeight());
    }


    /**
     * Returns the collision rectangle of the paddle.
     *
     * @return the collision rectangle of the paddle
     */
    public Rectangle getCollisionRectangle() {
        return paddleRect;

    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        regionWidth = (screenWidth - 2 * borderWidth) / NUBERS_OF_REGIONS;
        double xHit = collisionPoint.getX();
        double newAngle = 0;
        double fraction = this.paddleRect.getWidth() / NUBERS_OF_REGIONS;
        double xStart = this.paddleRect.getUpperLeft().getX();

        double partition1 = xStart + fraction;
        double partition2 = xStart + 2 * fraction;
        double partition3 = xStart + 3 * fraction;
        double partition4 = xStart + 4 * fraction;

        if (xHit >= xStart && xHit < partition1) {
            newAngle = 300; // Leftmost region

        } else if (xHit >= partition1 && xHit < partition2) {
            newAngle = 330; // Second region

        } else if (xHit >= partition2 && xHit < partition3) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy()); // middle region

        } else if (xHit >= partition3 && xHit < partition4) {
            newAngle = 30; // Fourth region

        } else if (xHit >= partition4 && xHit < xStart + this.paddleRect.getWidth()) {
            newAngle = 60; // Rightmost region
        }
        return Velocity.fromAngleAndSpeed(newAngle, currentVelocity.getSpeed());
    }

    /**
     * Adds the paddle to a given arkanoid.Game object.
     *
     * @param g The arkanoid.Game object to which the sprite object will be added.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite((Sprite) this);
    }
}







