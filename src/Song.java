import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Song {
    private String nameOfSong;
    private String artist;
    private String currentImagePathName;
    private String currentAudioPathName;
    private int duration; // in seconds

    private int currentTime = 0;
    private long lastUpdateMillis = 0;

    private boolean isPlaying = false;
    private Clip clip;
    private long clipMicrosecondPos = 0;

    public Song(String nameOfSong, String artist, String currentImagePathName, int duration, String currentAudioPathName) {
        this.nameOfSong = nameOfSong;
        this.artist = artist;
        this.currentImagePathName = currentImagePathName;
        this.currentAudioPathName = currentAudioPathName;
        this.duration = duration;
        loadClip();
    }

    private void loadClip() {
        try {
            File audioFile = new File("res/" + currentAudioPathName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }
    }

    public void togglePlay() {
        if (clip == null) return;

        if (isPlaying) {
            pause();
        } else {
            resume();
        }
    }

    public void pause() {
        if (clip != null && isPlaying) {
            clipMicrosecondPos = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
        }
    }

    public void resume() {
        if (clip != null && !isPlaying) {
            clip.setMicrosecondPosition(clipMicrosecondPos);
            clip.start();
            lastUpdateMillis = System.currentTimeMillis();
            isPlaying = true;
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            clipMicrosecondPos = 0;
        }
        isPlaying = false;
        currentTime = 0;
    }

    // Called from simulation loop every frame
    public void updateCurrentTime() {
        if (!isPlaying) return;

        long now = System.currentTimeMillis();
        if (now - lastUpdateMillis >= 1000) {
            currentTime++;
            lastUpdateMillis = now;
        }

        if (currentTime >= duration) {
            stop();
        }
    }

    // --- Getters ---
    public String getNameOfSong() { return nameOfSong; }
    public String getArtist() { return artist; }
    public String getCurrentImagePathName() { return currentImagePathName; }
    public String getCurrentAudioPathName() { return currentAudioPathName; }
    public int getDuration() { return duration; }
    public int getCurrentTime() { return currentTime; }
    public boolean isPlayingSong() { return isPlaying; }

    public void playSong() {
    }
}
