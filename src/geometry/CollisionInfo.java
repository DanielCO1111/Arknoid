package geometry;

import arkanoid.Collidable;
//Daniel Cohen 209313311
//Yona Dassa 211950340

/** Class holding the required information about the collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Creates a new CollisionInfo object.
     *
     * @param collisionObject the collidable object that was hit
     * @param collisionPoint  the point of collision between the ball and the collidable object
     */

    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        // this.trajectory = trajectory;
        this.collisionObject = collisionObject;
        this.collisionPoint = collisionPoint;

    }

    /**
     * the point at which the collision occurs.
     *
     * @return The point of collision.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * The collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}


