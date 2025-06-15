import java.awt.*;
import java.util.*;
import java.util.List;

public class Simulation {
    public static int size = 800;
    private int level = 0;
    public static Color backgroundColor = Color.black;
    private List<Circle> circles = new ArrayList<>();
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
    }

    public Simulation(int level) {
        this.level = level;
    }

    public void run() {
        for (Circle circle : circles) {
//            circle.setStartAngle(circle.getStartAngle() + 1);
//            circle.setEndAngle(circle.getEndAngle() + 1);
            circle.autoUpdateAnglesRandomly();
            circle.draw();
        }
    }
}
