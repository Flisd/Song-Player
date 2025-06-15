public class Song {
    private String title;

    private String artist;

    String currentImagePathName;
    String nextImagePathName;
    String nextNextImagePathName;

    String currentAudioPathName;

    private int duration; // in seconds
    
    private int currentTime = 0; // in seconds

    private long lastUpdateMillis = 0;

    private boolean isPlaying = true;

    public Song() {
        this.title = "Red Dead Redemption 2";
        this.artist = "Rockstar Games";
        this.currentImagePathName = "redDead.png";
        this.nextImagePathName = "redDead.png";
        this.nextNextImagePathName = "redDead.png";
        this.currentAudioPathName = "redDead.mp3";
        this.duration = 10;
    }

    public void next(){
        update();
    }

    public void togglePlay() {
        isPlaying = !isPlaying;
    }

    public void previous(){
        update();

    }

    public void update() {

    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCurrentImagePathName() {
        return currentImagePathName;
    }

    public String getNextImagePathName() {
        return nextImagePathName;
    }

    public String getNextNextImagePathName() {
        return nextNextImagePathName;
    }

    public String getCurrentAudioPathName() {
        return currentAudioPathName;
    }

    public int getDuration() {
        return duration;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void updateCurrentTime() {
        long now = System.currentTimeMillis();
        if (now - lastUpdateMillis >= 1000 && isPlaying) {
            currentTime += 1;
            lastUpdateMillis = now;
        }
    }

    public boolean isPlayingSong() {
        return isPlaying;
    }
}
