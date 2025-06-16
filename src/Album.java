import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Album {
    public static String nextImagePathName;
    public static String nextNextImagePathName;
    public static String currentImagePathName;


    HashMap<Integer,Song> songs = new HashMap<>();
    String songPath = "res/songsInfo.txt";

    public int currentSongIndex = 1;

    public Album(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(songPath));
        }catch (Exception ignored) {

        }

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split("~");
            if (parts.length == 6) {
                int songIndex = Integer.parseInt(parts[0].trim());
                String nameOfSong = parts[1].trim();
                String artist = parts[2].trim();
                currentImagePathName = parts[3].trim();
                int duration = Integer.parseInt(parts[4]);
                String currentAudioPathName = parts[5].trim();
                Song song = new Song(nameOfSong, artist, currentImagePathName, duration, currentAudioPathName);
                songs.put(songIndex, song);
            }
        }

        if(songs.size() < 3)
            nextImagePathName = nextNextImagePathName = currentImagePathName;

    }

    public Song getNextSong() {
        return songs.get(currentSongIndex);
    }

    public Song getSong(int index) {
        return songs.get(index);
    }
}
