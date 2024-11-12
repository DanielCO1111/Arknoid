package geometry;

//Daniel Cohen 209313311
//Yona Dassa 211950340

/**
 * The Point class represents a point in a 2D coordinate system.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructs a Point with the specified x and y coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the distance from this point to another point.
     * The distance is calculated using a distance formula.
     *
     * @param other the other point to which the distance is calculated
     * @return the Manhattan distance between this point and the other point
     */
    public double distance(Point other) {
        double distX = this.x - other.x;
        double distY = this.y - other.y;
        double absoluteDistX = distX * distX;
        double absoluteDistY = distY * distY;

        return Math.sqrt(absoluteDistX + absoluteDistY);
    }

    /**
     * Determines whether this point is equal to another point.
     * Two points are considered equal if they have the same x and y coordinates.
     *
     * @param other the other point to compare to
     * @return true if the points have the same coordinates, false otherwise
     */
    public boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Returns the x coordinate of this point.
     *
     * @return the x coordinate of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of this point.
     *
     * @return the y coordinate of this point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Setter of the x coordinate of the point.
     *
     * @param x the new x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter of the y coordinate of the point.
     *
     * @param y the new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
}
