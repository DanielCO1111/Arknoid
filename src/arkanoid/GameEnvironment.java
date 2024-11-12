//package arkanoid;
//
//// Daniel Cohen 209313311
//// Yona Dassa 211950340
//
//import geometry.CollisionInfo;
//import geometry.Line;
//import geometry.Point;
//import geometry.Rectangle;
//import java.util.ArrayList;
//import java.util.List;
//import java.awt.Color;
//
///**
// * The arkanoid.GameEnvironment class handles the environment of the game, including managing
// * collidable objects and determining collisions.
// */
//public class GameEnvironment {
//    private List<arkanoid.Collidable> collidables;
//
//    /**
//     * Constructs a new arkanoid.GameEnvironment object with an empty list of
//     * collidables.
//     */
//    public GameEnvironment() {
//        this.collidables = new ArrayList<>();
//    }
//    /**
//     * Adds a collidable to the environment.
//     *
//     * @param c the collidable to add
//     */
//    public void addCollidable(arkanoid.Collidable c) {
//        this.collidables.add(c);
//    }
//
//
//    /**
//     * Returns the information about the closest collision that is going to occur with the specified trajectory.
//     *
//     * @param trajectory the trajectory to check for collisions
//     * @return the CollisionInfo of the closest collision, or null if no collision is detected
//     */
//    public CollisionInfo getClosestCollision(Line trajectory) {
//        List<CollisionInfo> collisionList = new ArrayList<>();
//
//        for (arkanoid.Collidable collidable : collidables) {
//            Rectangle rec = collidable.getCollisionRectangle();
//            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(rec);
//            if (collisionPoint != null) {
//                collisionList.add(new CollisionInfo(collidable, collisionPoint));
//            }
//        }
//        if (collisionList.isEmpty()) {
//            return null;
//        }
//
//        double minDistance = Double.MAX_VALUE;
//        CollisionInfo closestCollision = null;
//
//        for (CollisionInfo info : collisionList) {
//            Block b = null;
//            if (info.collisionObject() instanceof Block) {
//                b = (Block) info.collisionObject();
//            }
//            if (b != null && b.getColor() == Color.CYAN) {
//                System.out.println("hi");
//            }
//
//            Point currentPoint = trajectory.start();
//            Point collisionPoint = info.collisionPoint();
//
//            double gap = currentPoint.distance(collisionPoint);
//
//            if (gap < minDistance) {
//                minDistance = gap;
//                closestCollision = info;
//            }
//        }
//        return closestCollision;
//    }
//
//    /**
//     * Removes a collidable from the environment.
//     *
//     * @param c the collidable to remove
//     */
//
//    public void removeCollidable(arkanoid.Collidable c) {
//        this.collidables.remove(c);
//
//    }
//
//}
//

package arkanoid;

import geometry.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * The GameEnvironment class handles the environment of the game, including managing
 * collidable objects and determining collisions.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructs a new GameEnvironment object with an empty list of
     * collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a collidable to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Returns the information about the closest collision that is going to occur with the specified trajectory.
     *
     * @param trajectory the trajectory to check for collisions
     * @return the CollisionInfo of the closest collision, or null if no collision is detected
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> collisionList = new ArrayList<>();

        for (Collidable collidable : collidables) {
            Rectangle rec = collidable.getCollisionRectangle();
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(rec);
            if (collisionPoint != null) {
                collisionList.add(new CollisionInfo(collidable, collisionPoint));
                // Ensure CollisionInfo accepts Collidable
            }
        }
        if (collisionList.isEmpty()) {
            return null;
        }

        double minDistance = Double.MAX_VALUE;
        CollisionInfo closestCollision = null;

        for (CollisionInfo info : collisionList) {
            Object obj = info.collisionObject(); // Use Object here
            if (obj instanceof Block) {
                Block b = (Block) obj;
                if (b.getColor() == Color.CYAN) {
                    System.out.println("hi");
                }
            }

            Point currentPoint = trajectory.start();
            Point collisionPoint = info.collisionPoint();
            double gap = currentPoint.distance(collisionPoint);

            if (gap < minDistance) {
                minDistance = gap;
                closestCollision = info;
            }
        }
        return closestCollision;
    }

    /**
     * Removes a collidable from the environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
}

