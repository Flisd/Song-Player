import java.awt.*;

public class Circle {
    private int radius;
    private int startAngle;
    private int endAngle;
    private Color color = Color.WHITE;

    public Circle(int radius, int startAngle, int endAngle) {
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
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

    public void draw() {
        StdDraw.setPenColor(Simulation.backgroundColor);
        StdDraw.circle(0, 0, radius);

        StdDraw.setPenColor(color);

        double angleStep = 0.01; // Adjust for smoother circle
        for (double angle = startAngle; angle <= endAngle; angle += angleStep) {
            double x = radius * Math.cos(Math.toRadians(angle));
            double y = radius * Math.sin(Math.toRadians(angle));
            StdDraw.point(x, y);
        }
    }
}
