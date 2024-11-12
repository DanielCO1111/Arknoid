package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;




/**
 * The arkanoid.Block class represents a rectangular block that can be hit and destroyed by a ball.
 * A block has a color and is defined by its bounding rectangle.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private final Rectangle rec;
    private boolean isPaddle;
    private Color color;
    private final List<HitListener> hitListeners;
    private boolean isDeathRegion;


    /**
     * Creates a new arkanoid.Block object with the specified bounding rectangle and color.
     *
     * @param rec   the bounding rectangle of the block
     * @param color the color of the block
     */

    public Block(Rectangle rec, java.awt.Color color) {

        this.rec = rec;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }
    /**
     * Creates a new arkanoid.Block object with the specified bounding rectangle, color, and death region status.
     *
     * @param rec           The bounding rectangle of the block
     * @param color         The color of the block
     * @param isDeathRegion Whether this block is a death region
     */
    public Block(Rectangle rec, java.awt.Color color, boolean isDeathRegion) {
        this.rec = rec;
        this.color = color;
        this.isDeathRegion = isDeathRegion;
        this.isPaddle = false;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Checks if this block is a death region.
     *
     * @return true if this block is a death region, false otherwise
     */
    public boolean isDeathRegion() {
        return this.isDeathRegion;
    }

    /**
     * Adds a hit listener to this block.
     *
     * @param hl the hit listener to add
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes a hit listener from this block.
     *
     * @param hl the hit listener to remove
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * Returns the color of this block.
     *
     * @return the color of the block
     */
    public java.awt.Color getColor() {
        return this.color;
    }


    /**
     * Returns the bounding rectangle of this block.
     *
     * @return the bounding rectangle of the block
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }

    /**
     * Returns a new velocity for a ball after hitting this block at the specified
     * collision point and with the specified
     * current velocity.
     *
     * @param collisionPoint  the point where the ball collided with the block
     * @param currentVelocity the current velocity of the ball
     * @return the new velocity of the ball after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        if (this.isDeathRegion) {
            this.notifyHit(hitter);
            return currentVelocity; // The ball will be removed, so we don't change its velocity
        }

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Get the edges of the rectangle
        Point upperLeft = this.rec.getUpperLeft();
        double recLeftX = upperLeft.getX();
        double recLeftY = upperLeft.getY();

        double rectRightX = recLeftX + this.rec.getWidth();
        double rectRightY = recLeftY + this.rec.getHeight();

        // Check if the collision point is on one of the vertical edges of the rectangle
        if (collisionPoint.getX() == recLeftX || collisionPoint.getX() == rectRightX) {
            dx = -dx;
        }
        // Check if the collision point is on one of the horizontal edges of the rectangle
        if (collisionPoint.getY() == recLeftY || collisionPoint.getY() == rectRightY) {
            dy = -dy;
        }

        if (!this.isPaddle && !this.isBorder() && !this.ballColorMatch(hitter)) {
            Color ballColor = hitter.getColor();
            hitter.setColor(this.color);
            this.color = ballColor;
            this.notifyHit(hitter);
        }

        return new Velocity(dx, dy);
    }
    /**
     * Checks if the color of the ball matches the color of this block.
     *
     * @param ball The ball to check against
     * @return true if the colors match, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }
    /**
     * Draws this block on the specified surface.
     *
     * @param d the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) rec.getUpperLeft().getX(), (int) rec.getUpperLeft().getY(), (int) rec.getWidth(),
                (int) rec.getHeight());
        if (!this.isBorder()) {
            d.setColor(Color.BLACK);
            d.drawRectangle((int) rec.getUpperLeft().getX(), (int) rec.getUpperLeft().getY(), (int) rec.getWidth(),
                    (int) rec.getHeight());
        }
    }


    /**
     * Updates the block's state for one time step.
     * This method is called as part of the game's animation loop.
     */
    @Override
    public void timePassed() {

    }
    /**
     * Adds this block to the specified game by adding it as a sprite and a collidable.
     *
     * @param g the game to add this block to
     */
    public void addToGame(Game g) {
        g.addSprite(this); //g.addSprite((arkanoid.Sprite)this);
        g.addCollidable(this);
    }

    /**
     * Removes this block from the specified game as a sprite and collidable object.
     *
     * @param game the game to remove this block from
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this); // Remove the block from the game's sprite collection
        game.removeCollidable(this); // Remove the block from the game's collidable objects
    }



    /**
     * Notifies all the hit listeners when a hit event occurs.
     *
     * @param hitter the ball that hit this block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Checks if this block is located on any border of the game window.
     *
     * @return true if the block is on any border, false otherwise
     */
    public boolean isBorder() {
        double x = rec.getUpperLeft().getX();
        double y = rec.getUpperLeft().getY();
        double width = rec.getWidth();
        double height = rec.getHeight();

        // Check if the block is on the top, bottom, left, or right border
        return x == 0 || y == 0 || x + width == 800 || y + height == 600;

    }

    /**
     * Checks if this block is located on the top border of the game window.
     *
     * @return true if the block is on the top border, false otherwise
     */
    public boolean isTopBorder() {
        double y = rec.getUpperLeft().getY();

        // Check if the block is on the top border
        return y == 0;
    }

    /**
     * Checks if this block is located on the bottom border of the game window.
     *
     * @return true if the block is on the bottom border, false otherwise
     */
    public boolean isBottomBorder() {
        double y = rec.getUpperLeft().getY() + rec.getHeight();

        // Check if the block is on the bottom border
        return y == 600;
    }

    /**
     * Checks if this block is located on the left border of the game window.
     *
     * @return true if the block is on the left border, false otherwise
     */
    public boolean isLeftBorder() {
        double x = rec.getUpperLeft().getX();

        // Check if the block is on the left border
        return x == 0;
    }

    /**
     * Checks if this block is located on the right border of the game window.
     *
     * @return true if the block is on the right border, false otherwise
     */
    public boolean isRightBorder() {
        double x = rec.getUpperLeft().getX();
        double width = rec.getWidth();

        // Check if the block is on the right border
        return (x + width) == 800;
    }

}




