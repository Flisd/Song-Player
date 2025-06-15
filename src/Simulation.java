import java.awt.*;
import java.util.*;
import java.util.List;

public class Simulation {
    public static int size = 800;
    private int level = 0;
    public static Color backgroundColor = Color.black;
    private List<Circle> circles = new ArrayList<>();
    private List<Bars> bars = new ArrayList<>();
    //private Ball ball;
    Random rand = new Random();

    public Simulation() {

        circles.add(new Circle(55, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(62, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(70, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(81, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(93, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(107, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(123, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(141, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(162, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        circles.add(new Circle(186, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        //ball = new Ball(0, 0, 10, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        bars.add(new Bars(250, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(280, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(310, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(340, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));

    }

    public Simulation(int level) {
        this.level = level;
    }

    public void run() {
        StdDraw.setScale(-size / 2.0, size / 2.0);
        while (true) {
            StdDraw.clear(StdDraw.BLACK);
            for (Circle circle : circles) {
                circle.autoUpdateAnglesRandomly();
                circle.draw();
            }
            for (Bars bar : bars) {
                bar.draw();
            }
            StdDraw.show();
            StdDraw.pause(16);
        }
    }
}
