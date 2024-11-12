package geometry;

//Daniel Cohen 209313311
//Yona Dassa 211950340


/**
 * The Velocity class represents a velocity in 2D space with dx (change in x-coordinate)
 * and dy (change in y-coordinate).
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity with the specified changes in x and y coordinates.
     *
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the change in x-coordinate.
     *
     * @return the change in x-coordinate
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the change in y-coordinate.
     *
     * @return the change in y-coordinate
     */
    public double getDy() {
        return dy;
    }
    /**
     * Returns the speed of the velocity, calculated as the Euclidean norm of dx and dy.
     *
     * @return the speed of the velocity
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
    /**
     * Sets the change in x-coordinate.
     *
     * @param dx the change in x-coordinate
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the change in y-coordinate.
     *
     * @param dy the change in y-coordinate
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Takes a point with position (x, y) and returns a new point with position (x+dx, y+dy).
     *
     * @param p the point to which the velocity is applied
     * @return a new point with the applied velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Constructs a velocity from an angle and a speed.
     *
     * @param angle the angle in degrees
     * @param speed the speed of the velocity
     * @return the Velocity object with the specified angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        // cos a = dx/hypotenuse
        double dx = speed * Math.cos(Math.toRadians(angle));

        // sin a = dy/ hypotenuse
        double dy = speed * Math.sin(Math.toRadians(angle));

        return new Velocity(dx, dy);
    }
}



