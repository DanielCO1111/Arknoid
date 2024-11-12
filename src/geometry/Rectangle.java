package geometry;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import java.util.ArrayList;

/**
 * The Rectangle class represents a rectangle defined by an upper-left point, width, and height.
 * It provides methods to compute intersection points with lines and access its properties.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Creates a new rectangle with the specified location and width/height.
     *
     * @param x      the x-coordinate of the upper-left corner of the rectangle
     * @param y      the y-coordinate of the upper-left corner of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this.upperLeft = new Point(x, y);
        this.width = width;
        this.height = height;
    }
    /**
     * Creates a new rectangle with the specified upper-left point, width, and height.
     *
     * @param upperLeft the upper-left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a (possibly empty) list of intersection points with the specified line.
     *
     * @param line the line to check for intersections with the rectangle
     * @return a list of intersection points
     */

    public java.util.List<Point> intersectionPoints(Line line) {

        double rectStartX = this.upperLeft.getX();
        double rectStartY = this.upperLeft.getY();

        double rectEndX = rectStartX + this.width;
        double rectEndY = rectStartY + this.height;

        //Point upperLeft = this.upperLeft;
        Point upperRight = new Point(rectEndX, rectStartY);
        Point downRight = new Point(rectEndX, rectEndY);
        Point downLeft = new Point(rectStartX, rectEndY);

        Line[] lines = new Line[4];
        java.util.List<Point> intersectionsList = null;

        // lines representing the edges of the rectangle
        lines[0] = new Line(this.upperLeft, upperRight);
        lines[1] = new Line(upperRight, downRight);
        lines[2] = new Line(downRight, downLeft);
        lines[3] = new Line(downLeft, this.upperLeft);

        for (int i = 0; i < 4; i++) {
            if (intersectionsList == null) {
                intersectionsList = new ArrayList<>();
            }
            Point interPoint = lines[i].intersectionWith(line);
            if (interPoint != null) {
                intersectionsList.add(interPoint);
            }
        }
        return intersectionsList;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {

        return this.height;
    }
    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return the upper-left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * Sets the upper-left point of the rectangle to the specified coordinates.
     *
     * @param x the x-coordinate of the new upper-left point
     * @param y the y-coordinate of the new upper-left point
     */
    public void setUpperLeft(double x, double y) {
        this.upperLeft = new Point(x, y);
    }
}



