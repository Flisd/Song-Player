import java.awt.*;
import java.util.*;
import java.util.List;

public class Simulation {
    public static int size = 900;

    public static Color backgroundColor = new Color(12, 1, 23);

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

    public Button[] favoriteSongButtons;
    public Button[] removeFavoriteSongButtons;

    String imagePath = album.getCurrentImagePathName();

    HashSet<String> nameOfSongsFavorite = new HashSet<>();

    String songToSearch = "";

    boolean buttonOneDrawImage, buttonTwoDrawImage, buttonThreeDrawImage, buttonFourDrawImage, buttonFiveDrawImage, buttonSixDrawImage;

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

        favoriteSongButtons = new Button[6];
        favoriteSongButtons[0] = new Button(-300, -400, 70, 70);
        favoriteSongButtons[1] = new Button(-175, -400, 70, 70);
        favoriteSongButtons[2] = new Button(-50, -400, 70, 70);
        favoriteSongButtons[3] = new Button(75, -400, 70, 70);
        favoriteSongButtons[4] = new Button(200, -400, 70, 70);
        favoriteSongButtons[5] = new Button(325, -400, 70, 70);

        for (Button favoriteSongButton : favoriteSongButtons)
            favoriteSongButton.setGradient(startButtonColor, endButtonColor);


        favoriteSongButtons[0].setBackground(new Color(200, 200, 255));
        favoriteSongButtons[0].setEnabled(false);
        favoriteSongButtons[1].setBackground(new Color(220, 220, 240));
        favoriteSongButtons[1].setEnabled(false);
        favoriteSongButtons[2].setBackground(new Color(180, 210, 230));
        favoriteSongButtons[2].setEnabled(false);
        favoriteSongButtons[3].setBackground(new Color(210, 230, 250));
        favoriteSongButtons[3].setEnabled(false);
        favoriteSongButtons[4].setBackground(new Color(190, 220, 240));
        favoriteSongButtons[4].setEnabled(false);
        favoriteSongButtons[5].setBackground(new Color(170, 210, 220));
        favoriteSongButtons[5].setEnabled(false);

        removeFavoriteSongButtons = new Button[6];
        int height = 20;
        int yValue = -350;
        removeFavoriteSongButtons[0] = new Button(-300, yValue, 70, height, "🗑️");
        removeFavoriteSongButtons[1] = new Button(-175, yValue, 70, height, "🗑️");
        removeFavoriteSongButtons[2] = new Button(-50, yValue, 70, height, "🗑️");
        removeFavoriteSongButtons[3] = new Button(75, yValue, 70, height, "🗑️");
        removeFavoriteSongButtons[4] = new Button(200, yValue, 70, height, "🗑️");
        removeFavoriteSongButtons[5] = new Button(325, yValue, 70, height, "🗑️");

        Color removeButtonColorStart = new Color(255, 100, 100);
        Color removeButtonColorEnd = new Color(255, 150, 150);

        for (Button removeFavoriteSongButton : removeFavoriteSongButtons)
            removeFavoriteSongButton.setGradient(removeButtonColorStart, removeButtonColorEnd);

    }

    public void run() {
        StdDraw.clear(backgroundColor);

        setUpBarAndCircle();

        favoriteButton();

        durationButtonStuff();

        playButton();

        setUpSkipAndRewindButtons();

        searchSong();

        setUpPictures();
    }

    public void favoriteButton() {
        Button favoriteButton = new Button(-350, -150, 50, 50, "★");
        favoriteButton.setGradient(startButtonColor, endButtonColor);
        favoriteButton.update();
        favoriteButton.draw();

        for (Button favoriteSongButton : favoriteSongButtons) {
            favoriteSongButton.update();
            favoriteSongButton.draw();
        }

        for (Button removeFavoriteSongButton : removeFavoriteSongButtons) {
            removeFavoriteSongButton.update();
            removeFavoriteSongButton.draw();
        }

        if (favoriteButton.clicked) {
            if (!favoriteSongButtons[0].getText().equals(".") && !nameOfSongsFavorite.contains(imagePath)) {
                nameOfSongsFavorite.add(imagePath);
                favoriteSongButtons[0].setText(".");
                favoriteSongButtons[0].setComment(imagePath + "~" + album.getNextSong().getNameOfSong());
                favoriteSongButtons[0].setEnabled(true);
                buttonOneDrawImage = true;
            } else if (!favoriteSongButtons[1].getText().equals(".") && !nameOfSongsFavorite.contains(imagePath)) {
                nameOfSongsFavorite.add(imagePath);
                favoriteSongButtons[1].setText(".");
                favoriteSongButtons[1].setComment(imagePath + "~" + album.getNextSong().getNameOfSong());
                favoriteSongButtons[1].setEnabled(true);
                buttonTwoDrawImage = true;
            } else if (!favoriteSongButtons[2].getText().equals(".") && !nameOfSongsFavorite.contains(imagePath)) {
                nameOfSongsFavorite.add(imagePath);
                favoriteSongButtons[2].setText(".");
                favoriteSongButtons[2].setComment(imagePath + "~" + album.getNextSong().getNameOfSong());
                favoriteSongButtons[2].setEnabled(true);
                buttonThreeDrawImage = true;
            } else if (!favoriteSongButtons[3].getText().equals(".") && !nameOfSongsFavorite.contains(imagePath)) {
                nameOfSongsFavorite.add(imagePath);
                favoriteSongButtons[3].setText(".");
                favoriteSongButtons[3].setComment(imagePath + "~" + album.getNextSong().getNameOfSong());
                favoriteSongButtons[3].setEnabled(true);
                buttonFourDrawImage = true;
            } else if (!favoriteSongButtons[4].getText().equals(".") && !nameOfSongsFavorite.contains(imagePath)) {
                nameOfSongsFavorite.add(imagePath);
                favoriteSongButtons[4].setText(".");
                favoriteSongButtons[4].setComment(imagePath + "~" + album.getNextSong().getNameOfSong());
                favoriteSongButtons[4].setEnabled(true);
                buttonFiveDrawImage = true;
            } else if (!favoriteSongButtons[5].getText().equals(".") && !nameOfSongsFavorite.contains(imagePath)) {
                nameOfSongsFavorite.add(imagePath);
                favoriteSongButtons[5].setText(".");
                favoriteSongButtons[5].setComment(imagePath + "~" + album.getNextSong().getNameOfSong());
                favoriteSongButtons[5].setEnabled(true);
                buttonSixDrawImage = true;
            }
        }

        for (int i = 0; i < removeFavoriteSongButtons.length; i++) {
            if (removeFavoriteSongButtons[i].isClicked()) {
                favoriteSongButtons[i].setText("");
                favoriteSongButtons[i].setComment("");
                nameOfSongsFavorite.remove(favoriteSongButtons[i].getComment().split("~")[0]);
                favoriteSongButtons[i].setEnabled(false);
                switch (i) {
                    case 0:
                        buttonOneDrawImage = false;
                        break;
                    case 1:
                        buttonTwoDrawImage = false;
                        break;
                    case 2:
                        buttonThreeDrawImage = false;
                        break;
                    case 3:
                        buttonFourDrawImage = false;
                        break;
                    case 4:
                        buttonFiveDrawImage = false;
                        break;
                    case 5:
                        buttonSixDrawImage = false;
                        break;
                }
            }
        }

        if (buttonOneDrawImage)
            StdDraw.picture(favoriteSongButtons[0].getX(), favoriteSongButtons[0].getY(), "res/" + favoriteSongButtons[0].getComment().split("~")[0], 70, 70);
        if (buttonOneDrawImage)
            StdDraw.picture(favoriteSongButtons[0].getX(), favoriteSongButtons[0].getY(), "res/" + favoriteSongButtons[0].getComment().split("~")[0], 70, 70);
        if (buttonTwoDrawImage)
            StdDraw.picture(favoriteSongButtons[1].getX(), favoriteSongButtons[1].getY(), "res/" + favoriteSongButtons[1].getComment().split("~")[0], 70, 70);
        if (buttonThreeDrawImage)
            StdDraw.picture(favoriteSongButtons[2].getX(), favoriteSongButtons[2].getY(), "res/" + favoriteSongButtons[2].getComment().split("~")[0], 70, 70);
        if (buttonFourDrawImage)
            StdDraw.picture(favoriteSongButtons[3].getX(), favoriteSongButtons[3].getY(), "res/" + favoriteSongButtons[3].getComment().split("~")[0], 70, 70);
        if (buttonFiveDrawImage)
            StdDraw.picture(favoriteSongButtons[4].getX(), favoriteSongButtons[4].getY(), "res/" + favoriteSongButtons[4].getComment().split("~")[0], 70, 70);
        if (buttonSixDrawImage)
            StdDraw.picture(favoriteSongButtons[5].getX(), favoriteSongButtons[5].getY(), "res/" + favoriteSongButtons[5].getComment().split("~")[0], 70, 70);

        for (int i = 0; i < favoriteSongButtons.length; i++) {
            if (favoriteSongButtons[i].isClicked()) {
                Song oldSong = album.getNextSong();
                album.searchSong(favoriteSongButtons[i].getComment().split("~")[1]);
                if (oldSong != null && oldSong.isPlayingSong()) {
                    oldSong.stop();
                }
            }
        }

        imagePath = album.getCurrentImagePathName();
    }

    public void playButton() {
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

        if (album.getSong(album.currentSongIndex).isPlayingSong())
            album.getSong(album.currentSongIndex).playSong();
    }

    public void durationButtonStuff() {
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
    }

    public void setUpPictures() {
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
            Album.converter.createWav(album.getSong(album.currentSongIndex).getCurrentAudioPathNameMp3());
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
            Album.converter.createWav(album.getSong(album.currentSongIndex).getCurrentAudioPathNameMp3());
            if (lastNextPrevClickTime == 0 || currentTime - lastNextPrevClickTime >= 500) {
                lastNextPrevClickTime = currentTime;

                Song currentSong = album.getSong(album.currentSongIndex);
                if (currentSong != null)
                    currentSong.stop();


                album.previous();
            }
        }
        prevPressedLastFrame = prevPressedNow;
    }

    public void setUpSkipAndRewindButtons() {
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
    }

    public void setUpBarAndCircle() {
        for (Circle circle : circles) {
            circle.autoUpdateAnglesRandomly();
            circle.draw();
        }


        for (Bars bar : bars) {
            bar.draw();
        }
    }

    public void searchSong() {
        searchButton.setGradient(startButtonColor, endButtonColor);
        boolean enterPressed = false;
        while (StdDraw.hasNextKeyTyped()) {
            char temp = StdDraw.nextKeyTyped();
            char backspace = '\b';
            char enter = '\n';
            if (temp == backspace) {
                if (!songName.isEmpty()) songName.deleteCharAt(songName.length() - 1);
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
            if (!album.searchSong(songToSearch) && !album.searchSongNotExact(songToSearch)) {
                songName.append("Song not found: ").append(songToSearch);
                searchButton.setText(songToSearch);
            } else if (oldSong != null)
                oldSong.stop();
        }
        searchButton.setText(songName.toString().isEmpty() ? "Search Song: " : songName.toString());
        searchButton.update();
        searchButton.draw();
    }
}
