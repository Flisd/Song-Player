import java.awt.*;

public class Bars {
    int x;
    int y;
    double width;
    double height;
    Color color;
    double speed;

    public Bars(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        speed = Math.random()* 5 + 1; // Random speed between 1 and 5
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledRectangle(x, y, width / 2, height / 2);

        // Draw the moving rectangle (top bar)
        double topBarY = 350 - (350 - y) * (speed / 5.0);
        StdDraw.setPenColor(Simulation.backgroundColor);
        StdDraw.filledRectangle(x, topBarY, width / 2, 200);

        speed = ((speed + 1.5) % 5) * (Math.random() > 0.5 ? 1 : -1);

        StdDraw.setPenColor(color);
    }


}
