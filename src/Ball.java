import java.awt.*;

public class Ball {
    public double x, y;
    public double vx, vy;
    public double radius;
    private Color color;

    public Ball(double x, double y, double radius, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = new Color(r, g, b);
        this.vx = Math.random() * 6 - 3;
        this.vy = Math.random() * 6 - 3;
        if (vx == 0) vx = 1;
        if (vy == 0) vy = 1;
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x, y, radius);
    }

    public void update() {
        x += vx;
        y += vy;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }
}
