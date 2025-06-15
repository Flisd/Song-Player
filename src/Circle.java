import java.awt.*;
import java.util.Random;

public class Circle {
    private int radius;
    private int startAngle;
    private int endAngle;
    private Color color;
    private int randIncrement;

    public Circle(int radius, int startAngle, int endAngle, Color color) {
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.color = color;
        Random rand = new Random();
        this.randIncrement = rand.nextInt(5) + 1;
    }

    public void autoUpdateAnglesRandomly() {
        startAngle += randIncrement;
        endAngle += randIncrement;
    }

    public void draw() {
        StdDraw.setPenColor(Simulation.backgroundColor);
        StdDraw.circle(0, 0, radius);

        StdDraw.setPenColor(color);

        StdDraw.arc(0, 0, radius, startAngle, endAngle);
    }

    public boolean isCollision(double ballX, double ballY, double ballRadius, double centerX, double centerY) {
        double dx = ballX - centerX;
        double dy = ballY - centerY;
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        if (angle < 0) angle += 360;
        int start = ((startAngle % 360) + 360) % 360;
        int end = ((endAngle % 360) + 360) % 360;
        boolean inArc = (start < end) ? (angle >= start && angle <= end) : (angle >= start || angle <= end);
        double dist = Math.sqrt(dx * dx + dy * dy);
        boolean atRadius = Math.abs(dist - radius) < ballRadius;
        return inArc && atRadius;
    }

    public int getRadius() {
        return radius;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }
}
