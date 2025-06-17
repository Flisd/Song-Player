import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Simulation {
    public static int size = 900;

    public static Color backgroundColor = Color.black;

    private List<Circle> circles = new ArrayList<>();

    private List<Bars> bars = new ArrayList<>();

    Random rand = new Random();

    Album album = new Album();

    private boolean nextPressedLastFrame = false;
    private boolean prevPressedLastFrame = false;
    private long lastNextPrevClickTime = 0;
    private long lastSkipRewindClickTime = 0;
    private long lastPlayButtonClickTime = 0;

    public Button searchButton;

    private Color startButtonColor = new Color(104, 161, 190);
    private Color endButtonColor = new Color(168, 203, 227); // Red

    StringBuilder songName = new StringBuilder();

    String songToSearch = "";

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

        bars.add(new Bars(250, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(280, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(310, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));
        bars.add(new Bars(340, 180, 20, 350, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))));

        searchButton = new Button(0, 400, 700, 50, "Search Song: ");
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

        StdDraw.picture(-300, 200, "res/" + album.nextImagePathName, 150, 150);
        StdDraw.picture(-225, 125, "res/" + album.nextNextImagePathName, 70, 70);
        StdDraw.picture(-200, -150, "res/" + album.currentImagePathName, 200, 200);


        Font font = new Font("Monospaced", Font.BOLD, 25);
        StdDraw.setFont(font);

        Button nextButton = new Button(-150, -300, 70, 50, "Next");
        nextButton.setGradient(startButtonColor, endButtonColor);
        nextButton.update();
        nextButton.draw();


        Button previousButton = new Button(-250, -300, 70, 50, "Prev");
        previousButton.setGradient(startButtonColor, endButtonColor);
        previousButton.update();
        previousButton.draw();

        font = new Font("Monospaced", Font.BOLD, 20);
        StdDraw.setFont(font);

        Button NameOfSong = new Button(125, -75, 450, 40, album.getNextSong().getNameOfSong().substring(0, Math.min(album.getNextSong().getNameOfSong().length(), 30)));
        NameOfSong.setGradient(startButtonColor, endButtonColor);
        NameOfSong.update();
        NameOfSong.draw();

        Button NameOfArtist = new Button(13, -125, 225, 40, album.getNextSong().getArtist().substring(0, Math.min(album.getNextSong().getArtist().length(), 15)));
        NameOfArtist.setGradient(startButtonColor, endButtonColor);
        NameOfArtist.update();
        NameOfArtist.draw();


        long currentTime = System.currentTimeMillis();


        boolean nextPressedNow = nextButton.isClicked();
        if (nextPressedNow && !nextPressedLastFrame) {
            if (lastNextPrevClickTime == 0 || currentTime - lastNextPrevClickTime >= 500) {
                lastNextPrevClickTime = currentTime;

                Song currentSong = album.getSong(album.currentSongIndex);
                if (currentSong != null)
                    currentSong.stop();


                album.next();
            }
        }
        nextPressedLastFrame = nextPressedNow;


        boolean prevPressedNow = previousButton.isClicked();
        if (prevPressedNow && !prevPressedLastFrame) {
            if (lastNextPrevClickTime == 0 || currentTime - lastNextPrevClickTime >= 500) {
                lastNextPrevClickTime = currentTime;

                Song currentSong = album.getSong(album.currentSongIndex);
                if (currentSong != null)
                    currentSong.stop();


                album.previous();
            }
        }
        prevPressedLastFrame = prevPressedNow;


        Button durationButton = new Button(150, -200, 450, 40);
        durationButton.setGradient(startButtonColor, endButtonColor);
        durationButton.update();
        durationButton.draw();

        int maxHalfWidth = 220;
        double progress = Math.min(album.getNextSong().getCurrentTime() / (double) album.getNextSong().getDuration(), 1.0);
        double realHalfWidth = progress * maxHalfWidth;
        double centerX = 150 - maxHalfWidth + realHalfWidth;
        StdDraw.setPenColor(new Color(239, 223, 223, 169));
        StdDraw.filledRectangle(centerX, -200, realHalfWidth, 15);

        album.getNextSong().updateCurrentTime();

        Button playButton = new Button(150, -250, 70, 40, "▷");
        playButton.setGradient(startButtonColor, endButtonColor);
        playButton.update();
        playButton.draw();

        if (playButton.isClicked()) {
            long currentTime1 = System.currentTimeMillis();
            if (lastPlayButtonClickTime == 0 || currentTime1 - lastPlayButtonClickTime >= 500) {
                lastPlayButtonClickTime = currentTime1;
                album.getNextSong().togglePlay();
                System.out.println("Play/Pause clicked. is playing: " + album.getNextSong().isPlayingSong());
            }
        }

        if (album.getSong(album.currentSongIndex).isPlayingSong()) {
            album.getSong(album.currentSongIndex).playSong();
        }

        Button rewindButton = new Button(50, -250, 100, 40, "<< 5s");
        rewindButton.setGradient(startButtonColor, endButtonColor);
        rewindButton.update();
        rewindButton.draw();

        Button skipButton = new Button(250, -250, 100, 40, "5s >>");
        skipButton.setGradient(startButtonColor, endButtonColor);
        skipButton.update();
        skipButton.draw();

        Song currentSong = album.getSong(album.currentSongIndex);

        long currentTime2 = System.currentTimeMillis();

        if (rewindButton.isClicked()) {
            if (lastSkipRewindClickTime == 0 || currentTime2 - lastSkipRewindClickTime >= 500) {
                lastSkipRewindClickTime = currentTime2;
                int newTime = currentSong.getCurrentTime() - 5;
                if (newTime < 0)
                    newTime = 0;
                currentSong.setCurrentTime(newTime);
            }
        }

        if (skipButton.isClicked()) {
            if (lastSkipRewindClickTime == 0 || currentTime2 - lastSkipRewindClickTime >= 500) {
                lastSkipRewindClickTime = currentTime2;
                int newTime = currentSong.getCurrentTime() + 5;
                if (newTime > currentSong.getDuration())
                    newTime = currentSong.getDuration();
                currentSong.setCurrentTime(newTime);
            }
        }

        searchSong();

    }

    public void searchSong() {
        searchButton.setGradient(startButtonColor, endButtonColor);
        boolean enterPressed = false;
        while (StdDraw.hasNextKeyTyped()) {
            char temp = StdDraw.nextKeyTyped();
            char backspace = '\b';
            char enter = '\n';

            if (temp == backspace) {
                if (!songName.isEmpty())
                    songName.deleteCharAt(songName.length() - 1);
            } else if (temp == enter) {
                enterPressed = true;
            } else if (songName.length() <= 40) {
                songName.append(temp);
            }
        }

        if (enterPressed) {
            songToSearch = songName.toString();
            System.out.println("Searching for song: " + songToSearch);
            songName = new StringBuilder();
            Song oldSong = album.getNextSong();
            if (!album.searchSong(songToSearch)) {
                songName.append("Song not found: ").append(songToSearch);
                searchButton.setText(songToSearch);
            } else
                if (oldSong != null)
                    oldSong.stop();

        }

        searchButton.setText(songName.toString().isEmpty() ? "Search Song: " : songName.toString());
        searchButton.update();
        searchButton.draw();



    }
}
