import javax.xml.namespace.QName;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Simulation {
    public static int size = 800;

    public static Color backgroundColor = Color.black;

    private List<Circle> circles = new ArrayList<>();

    private List<Bars> bars = new ArrayList<>();

    Random rand = new Random();

    Song song = new Song();


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
        //circles.add(new Circle(186, 0, 270, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        //ball = new Ball(0, 0, 10, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        bars.add(new Bars(250, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(280, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(310, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(340, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));

    }

    public void run() {

        StdDraw.clear(backgroundColor);
        for (Circle circle : circles) {
            circle.autoUpdateAnglesRandomly();
            circle.draw();
        }


        for (Bars bar : bars) {
            bar.draw();
        }

        StdDraw.picture(-300, 200, "res/" + song.nextImagePathName, 150, 150);
        StdDraw.picture(-225, 125, "res/" + song.nextNextImagePathName, 70, 70);
        StdDraw.picture(-200, -150, "res/" + song.currentImagePathName, 200, 200);


        Font font = new Font("Monospaced", Font.BOLD, 25);
        StdDraw.setFont(font);

        Button nextButton = new Button(-150, -300, 70, 50, "Next");
        nextButton.setGradient(Color.ORANGE, Color.RED);
        nextButton.update();
        nextButton.draw();

        Button previousButton = new Button(-250, -300, 70, 50, "Prev");
        previousButton.setGradient(Color.ORANGE, Color.RED);
        previousButton.update();
        previousButton.draw();

        font = new Font("Monospaced", Font.BOLD, 20);
        StdDraw.setFont(font);

        Button NameOfSong = new Button(125, -75, 450, 40, song.getTitle());
        NameOfSong.setGradient(Color.ORANGE, Color.RED);
        NameOfSong.update();
        NameOfSong.draw();

        Button NameOfArtist = new Button(13, -125, 225, 40, song.getArtist());
        NameOfArtist.setGradient(Color.ORANGE, Color.RED);
        NameOfArtist.update();
        NameOfArtist.draw();


        if (nextButton.isClicked())
            song.next();
        else if (previousButton.isClicked())
            song.previous();

        Button durationButton = new Button(150, -200, 450, 40);
        durationButton.setGradient(Color.ORANGE, Color.RED);
        durationButton.update();
        durationButton.draw();
        double progress = song.getCurrentTime() / (double) song.getDuration();
        durationButton.drawProgressBar(progress, Color.GREEN);

        Button playButton = new Button(150, -250, 70, 40, "▷");
        playButton.setGradient(Color.ORANGE, Color.RED);
        playButton.update();
        playButton.draw();
        if (playButton.isClicked())
            song.togglePlay();

    }
}
