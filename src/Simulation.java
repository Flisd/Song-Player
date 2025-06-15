import java.awt.*;

public class Simulation {
    public int size = 800;
    private int level = 0;
    public static Color backgroundColor = Color.BLACK;

    public Simulation() {

    }

    public Simulation(int level) {
        this.level = level;
    }

    public void run() {
        // set up canvas
        StdDraw.setCanvasSize(size, size); //set up drawing canvas
        StdDraw.setXscale((double) -size / 2, (double) size / 2); // Center is now <0, 0>
        StdDraw.setYscale((double) -size / 2, (double) size / 2);
        StdDraw.setPenRadius(0.01);

        StdDraw.clear(backgroundColor);
        Circle circle = new Circle(100, 0, 270);
        circle.draw();
    }
}
