package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * The arkanoid.arkanoid.Collidable interface represents objects that can be collided with.
 * It defines methods for getting the collision shape and handling collisions.
 */
public interface Collidable {
    /**
     * Returns the "collision shape" of the object.
     *
     * @return the collision rectangle of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred at the specified collision point with the given velocity.
     * The method returns the new velocity expected after the hit, based on the force the object inflicted.
     *
     * @param hitter          the ball which hits the rectangle
     * @param collisionPoint  the point at which the collision occurred
     * @param currentVelocity the velocity of the ball before the collision
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
