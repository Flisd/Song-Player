import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Album {
    public static String currentImagePathName;
    public static String nextImagePathName;
    public static String nextNextImagePathName;
    private int totalNumOfSongs;

    private final HashMap<Integer, Song> songs = new HashMap<>();
    private final String songPath = "res/songsInfo.txt";

    public int currentSongIndex = 1; // 1-based indexing

    public Album() {
        loadSongsFromFile();

        if (songs.isEmpty()) {
            System.out.println("No songs found in " + songPath);
            return;
        }

        updateImagePaths();
    }

    public int getTotalNumOfSongs() {
        return totalNumOfSongs;
    }

    private void loadSongsFromFile() {
        try (Scanner scanner = new Scanner(new File(songPath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("~");
                if (parts.length == 6) {
                    int songIndex = Integer.parseInt(parts[0].trim());
                    String nameOfSong = parts[1].trim();
                    String artist = parts[2].trim();
                    String imagePath = parts[3].trim();
                    int duration = Integer.parseInt(parts[4].trim());
                    String audioPath = parts[5].trim();
                    Song song = new Song(nameOfSong, artist, imagePath, duration, audioPath);
                    System.out.println(song.getCurrentAudioPathNameMp3());
                    songs.put(songIndex, song);
                    totalNumOfSongs++;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load songs: " + e.getMessage());
        }
    }

    private int getWrappedIndex(int baseIndex) {
        int size = songs.size();
        if (size == 0) return baseIndex;
        int wrapped = ((baseIndex - 1) % size + size) % size + 1;
        return songs.containsKey(wrapped) ? wrapped : 1;
    }

    private void updateImagePaths() {
        currentImagePathName = songs.get(currentSongIndex).getCurrentImagePathName();

        if (songs.size() >= 3) {
            nextImagePathName = songs.get(getWrappedIndex(currentSongIndex + 1)).getCurrentImagePathName();
            nextNextImagePathName = songs.get(getWrappedIndex(currentSongIndex + 2)).getCurrentImagePathName();
        } else {
            nextImagePathName = currentImagePathName;
            nextNextImagePathName = currentImagePathName;
        }
    }

    public Song getNextSong() {
        return songs.get(currentSongIndex);
    }

    public Song getSong(int index) {
        return songs.get(index);
    }

    public void next() {
        currentSongIndex = getWrappedIndex(currentSongIndex + 1);
        updateImagePaths();
    }

    public void previous() {
        currentSongIndex = getWrappedIndex(currentSongIndex - 1);
        updateImagePaths();
    }

    public Map<Integer, Song> getAllSongs() {
        return songs;
    }

    public boolean searchSong(String songToSearch) {
        for (Song song : songs.values()) {
            if (song.getNameOfSong().equalsIgnoreCase(songToSearch)) {
                currentSongIndex = songs.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(song))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse(currentSongIndex);
                updateImagePaths();
                return true;
            }
        }
        return false;
    }

    public String getCurrentImagePathName() {
        return currentImagePathName;
    }

    public boolean searchSongNotExact(String songToSearch) {
        if (songToSearch == null || songToSearch.isEmpty())
            return false;

        String lowerSearch = songToSearch.toLowerCase();
        for (Map.Entry<Integer, Song> entry : songs.entrySet()) {
            Song song = entry.getValue();
            if (song.getNameOfSong().toLowerCase().contains(lowerSearch)) {
                currentSongIndex = entry.getKey();
                updateImagePaths();
                return true;
            }
        }

        return false;
    }
}