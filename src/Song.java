import javax.sound.sampled.*;
import java.io.File;
public class Song implements Runnable {
    private String nameOfSong;
    private String artist;
    String currentImagePathName;
    String currentAudioPathName;
    private int duration; // in seconds
    private int currentTime = 0; // in seconds
    private long lastUpdateMillis = 0;
    private boolean isPlaying = false;
    int songIndex = 0;
    private long clipTimePosition = 0;
    private Clip clip;
    private Thread playThread;
    private boolean stopRequested = false;

    public Song(String nameOfSong, String artist, String currentImagePathName, int duration, String currentAudioPathName) {
        this.nameOfSong = nameOfSong;
        this.artist = artist;
        this.currentImagePathName = currentImagePathName;
        this.currentAudioPathName = currentAudioPathName;
        this.duration = duration;
    }

    public void next() {
        update();
    }

    public void togglePlay() {
        if (isPlaying) {
            pauseSong();
        } else {
            resumeSong();
        }
        isPlaying = !isPlaying;
    }

    public void pauseSong() {
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
        }
    }

    public void resumeSong() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
            isPlaying = true;
        }
    }

    public void previous() {
        update();
    }

    public void update() {
    }

    public String getNameOfSong() {
        return nameOfSong;
    }

    public String getArtist() {
        return artist;
    }

    public String getCurrentImagePathName() {
        return currentImagePathName;
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

    public void playSong() {
        if (playThread != null && playThread.isAlive()) {
            stopRequested = true;
            try {
                playThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        stopRequested = false;
        playThread = new Thread(this);
        playThread.start();
    }

    public void stopSong() {
        stopRequested = true;
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (playThread != null && playThread.isAlive()) {
            try {
                playThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        try {
            isPlaying = true;
            currentTime = 0;
            lastUpdateMillis = System.currentTimeMillis();

            File audioFile = new File("res/" + currentAudioPathName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            while (!stopRequested && clip.isRunning()) {
                updateCurrentTime(); // properly handles 1-second ticks
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isPlaying = false;
        }
    }

}