package geometry;

//Daniel Cohen 209313311
//Yona Dassa 211950340

/**
 * The Line class represents a line segment defined by a start and end point.
 */

public class Line {
    private Point start;
    private Point end;
    private static final double EPSILON = 0.000001d;
    private int intersectionBit = 0;

    /**
     * Constructs a Line with the specified start and end points.
     *
     * @param start the start point of the line
     * @param end   the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a Line with the specified coordinates for start and end points.
     *
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double middleX = (start.getX() + end.getX()) / 2;
        double middleY = (start.getY() + end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other the other line.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        }
        if (this.intersectionWith(other) == null) {
            if (intersectionBit == 1) {
                intersectionBit = 0;
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Returns true if this 2 lines intersect with this line, false otherwise.
     *
     * @param other1 is one of the other line we get.
     * @param other2 is the second  other line we get.
     * @return true if this 2 lines intersect with this line, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        if (other1 == null || other2 == null) {
            return false;
        }
        return (isIntersecting(other1) && isIntersecting(other2));
    }

    /**
     * Checks if a value is within the bounds defined by two other values.
     *
     * @param var   the value to check
     * @param start the start bound
     * @param end   the end bound
     * @return true if y is within the bounds, false otherwise
     */
    public static boolean isWithinBounds(double var, double start, double end) {
        return var >= Math.min(start, end) && var <= Math.max(start, end);
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other the other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point intersectionWith(Line other) {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        // Check if lines are parallel or coincident
        if (Math.abs(denominator) < EPSILON) {
            // Check if the lines are collinear
            if (isCollinear(other)) {
                // Check for overlapping segments
                if (isOverlapping(other)) {
                    if ((isPointOnLine(this.start, other)
                            && isPointOnLine(this.end, other)) || (isPointOnLine(other.start, this)
                            && isPointOnLine(other.end, this))) {
                        intersectionBit = 1;
                        return null;
                        // Check if they are overlapping at the edges
                    } else if (this.start.equals(other.end) || this.start.equals(other.start)) {
                        return this.start;
                    } else if (this.end.equals(other.start) || this.end.equals(other.end)) {
                        return this.end;
                    }
                    intersectionBit = 1;
                    return null;
                }
                return null; // Lines are collinear but not overlapping
            }
            return null; // Lines are parallel but not collinear
        }

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        if (ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1) {
            double intersectionX = x1 + ua * (x2 - x1);
            double intersectionY = y1 + ua * (y2 - y1);
            return new Point(intersectionX, intersectionY);
        }

        return null; // Lines do not intersect within the line segments
    }

    /**
     * Checks if this line is collinear with another line.
     *
     * @param other the other line
     * @return true if the lines are collinear, false otherwise
     */
    private boolean isCollinear(Line other) {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        return (Math.abs((y2 - y1) * (x3 - x1) - (x2 - x1) * (y3 - y1))
                < EPSILON && Math.abs((y2 - y1) * (x4 - x1) - (x2 - x1) * (y4 - y1)) < EPSILON);
    }
    /**
     * Checks if a point is on a given line.
     *
     * @param p the point to check
     * @param l the line to check against
     * @return true if the point is on the line, false otherwise
     */
    private boolean isPointOnLine(Point p, Line l) {
        double x1 = l.start.getX();
        double y1 = l.start.getY();
        double x2 = l.end.getX();
        double y2 = l.end.getY();
        double px = p.getX();
        double py = p.getY();

        return Math.abs((y2 - y1) * (px - x1) - (x2 - x1) * (py - y1))
                < EPSILON && isWithinBounds(px, x1, x2) && isWithinBounds(py, y1, y2);
    }
    /**
     * Checks if this line is overlapping with another line.
     *
     * @param other the other line
     * @return true if the lines are overlapping, false otherwise
     */
    private boolean isOverlapping(Line other) {
        return (isWithinBounds(other.start.getX(), this.start.getX(), this.end.getX())
                || isWithinBounds(other.end.getX(), this.start.getX(), this.end.getX())
                || isWithinBounds(this.start.getX(), other.start.getX(), other.end.getX())
                || isWithinBounds(this.end.getX(), other.start.getX(), other.end.getX()))
                && (isWithinBounds(other.start.getY(), this.start.getY(), this.end.getY())
                || isWithinBounds(other.end.getY(), this.start.getY(), this.end.getY())
                || isWithinBounds(this.start.getY(), other.start.getY(), other.end.getY())
                || isWithinBounds(this.end.getY(), other.start.getY(), other.end.getY()));
    }

    /**
     * Returns true if the lines are equal, false otherwise.
     *
     * @param other the other line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        if ((this.start.getY() == other.start.getY()) && (this.end.getX() == other.end.getX())
                && (this.start.getX() == other.start.getX()) && (this.end.getX() == other.end.getX())) {
            return true;
        } else if ((this.start.getY() == other.end.getY()) && (this.end.getX() == other.start.getX())
                && (this.start.getX() == other.end.getX()) && (this.end.getX() == other.start.getX())) {
            return true;
        }
        return false;
    }

    /**
     * Finds the closest intersection point to the start of the line with a given rectangle.
     *
     * @param rect the rectangle to check for intersection points with the line.
     * @return the closest intersection point to the start of the line, or null if there are no intersection points.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Line line = new Line(this.start, this.end);
        java.util.List<Point> intersectionsList = rect.intersectionPoints(line);

        if ((intersectionsList == null) || intersectionsList.isEmpty()) {
            return null;
        }

        double minDist = intersectionsList.get(0).distance(start);
        int index = 0;

        for (Point interPoint:intersectionsList) {
            if (interPoint.distance(start) < minDist) {
                minDist = interPoint.distance(start);
                index++;
            }
        }
        return intersectionsList.get(index);
    }
}
