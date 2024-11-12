package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import geometry.CollisionInfo;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Ball class represents a ball in the game.
 * It has a center point, radius, color, velocity, and a game environment.
 */
public class Ball implements Sprite {
    private Point center;
    private final int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private final GameEnvironment environment;

    /**
     * Constructs a new sprites.Ball with the given center position, radius and color.
     *
     * @param x           x coordinate starting point
     * @param y           y coordinate starting point
     * @param radius      radius of circle
     * @param color       of circle
     * @param environment the environment in which the balls move
     */
    public Ball(int x, int y, int radius, Color color, GameEnvironment environment) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
        this.environment = environment;
        Velocity velocity = new Velocity(1, 1);
        this.setVelocity(velocity);
    }

    /**
     * Constructs a new sprites.Ball with the given center position, radius and color.
     *
     * @param center      starting point
     * @param radius      radius of circle
     * @param color       color of circle
     * @param environment the environment in which the balls move
     */
    public Ball(Point center, int radius, java.awt.Color color, GameEnvironment environment) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.environment = environment;
    }

    /**
     * Returns the X coordinate of the center.
     *
     * @return the X coordinate of the center of the circle
     */

    public double getX() {
        return center.getX();
    }

    /**
     * Returns the Y coordinate of the center.
     *
     * @return the Y coordinate of the center of the circle
     */
    public double getY() {
        return center.getY();
    }

    /**
     * Returns the color of this circle.
     *
     * @return the color of this circle
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the ball.
     *
     * @param newColor The new color to set for the ball
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * Returns the velocity of this circle.
     *
     * @return the velocity of this circle
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the velocity to set
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;

    }


    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on
     */
    // @Override
    public void drawOn(DrawSurface surface) {
        int x = (int) (this.center.getX());
        int y = (int) (this.center.getY());
        int radius = this.radius;
        surface.setColor(this.color);
        surface.fillCircle(x, y, radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle(x, y, radius);
    }

    /**
     * Calculates the trajectory of the ball based on its current velocity.
     *
     * @return a Line representing the trajectory of the ball from its current position
     * to the position it would move to if it didn't collide with anything.
     */
    public Line calculateTrajectory() {
        Point p = velocity.applyToPoint(center);
        return new Line(this.center, p);
    }

    /**
     * Moves the ball one step using the current velocity width, height and
     * offset.The ball bounces from the sides and top/bottom of the
     * surface if it hits it. The new position is set post-moving.
     */
    public void moveOneStep() {
        Line trajectory = calculateTrajectory();
        Point end = trajectory.end();

        if (end.getX() < center.getX()) {
            end.setX(end.getX() - radius);
        } else {
            end.setX(end.getX() + radius);
        }
        if (end.getY() < center.getY()) {
            end.setY(end.getY() - radius);
        } else {
            end.setY(end.getY() + radius);
        }

        CollisionInfo collisionInfo = environment.getClosestCollision(trajectory);

        // no collisions, the ball can be safely moved to the end of the line
        if (collisionInfo == null) {
            this.center = trajectory.end();
            return;
        }

        // else, move the ball "almost" to the point of the collision
        Point collisionPoint = collisionInfo.collisionPoint();
        Velocity prevVelocity = velocity;
        //setVelocity(collisionInfo.collisionObject().hit(collisionPoint, velocity));
        setVelocity(collisionInfo.collisionObject().hit(this, collisionPoint, velocity));


        if (center.getX() == end.getX()) { // vertical
            if (velocity.getDy() < 0) {
                this.center.setY(collisionPoint.getY() - radius);
            } else {
                this.center.setY(collisionPoint.getY() + radius);
            }
        } else if (center.getY() == end.getY()) { // horizontal
            if (velocity.getDx() < 0) {
                this.center.setX(collisionPoint.getX() - radius);
            } else {
                this.center.setX(collisionPoint.getX() + radius);
            }
        } else { // slope
            double slope = (end.getY() - center.getY()) / (end.getX() - center.getX());
            double dx = Math.abs(radius / slope);
            if (prevVelocity.getDy() != velocity.getDy()) {
                if (velocity.getDy() > 0) {
                    this.center.setY(collisionPoint.getY() + radius);
                } else {
                    this.center.setY(collisionPoint.getY() - radius);
                }
                if (prevVelocity.getDx() < 0) {
                    this.center.setX(collisionPoint.getX() + dx);
                } else {
                    this.center.setX(collisionPoint.getX() - dx);
                }
            }
            if (prevVelocity.getDx() != velocity.getDx()) {
                if (velocity.getDx() > 0) {
                    this.center.setX(collisionPoint.getX() + dx);
                } else {
                    this.center.setX(collisionPoint.getX() - dx);
                }
                if (prevVelocity.getDy() < 0) {
                    this.center.setY(collisionPoint.getY() + radius);
                } else {
                    this.center.setY(collisionPoint.getY() - radius);
                }
            }
        }
        // Set new position
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Adds the ball to a arkanoid.Game object.
     *
     * @param g The arkanoid.Game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Removes the ball from the given game.
     *
     * @param game The arkanoid.Game object from which to remove this ball
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);

    }
}

