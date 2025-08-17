import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

public class Converter {

    private final File wavFolder;
    private final File resFolder;

    public Converter() {
        this.wavFolder = new File("C:\\Users\\Tijil\\OneDrive\\Desktop\\Java projects\\SongPlayer\\WavFolder");
        if (!wavFolder.exists()) wavFolder.mkdirs();

        this.resFolder = new File("C:\\Users\\Tijil\\OneDrive\\Desktop\\Java projects\\SongPlayer\\res");
        if (!resFolder.exists()) resFolder.mkdirs();
    }

    // Converts MP3 to WAV (only if missing)
    public File createWav(String mp3FilePath) {
        File mp3File = new File(mp3FilePath);
        String baseName = mp3File.getName().replaceAll("\\.mp3$", "");
        File wavFile = new File(wavFolder, baseName + ".wav");

        if (wavFile.exists()) {
            System.out.println("WAV already exists: " + wavFile.getAbsolutePath());
            return wavFile;
        }

        try {
            AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3File);
            AudioFormat baseFormat = mp3Stream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream pcmStream = AudioSystem.getAudioInputStream(targetFormat, mp3Stream);
            AudioSystem.write(pcmStream, AudioFileFormat.Type.WAVE, wavFile);

            System.out.println("WAV created at: " + wavFile.getAbsolutePath());
            return wavFile;

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException("Failed to convert MP3 to WAV", e);
        }
    }

    // Clears all WAV files in the folder
    public void clearWav() {
        File[] files = wavFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
        if (files != null) {
            for (File f : files) {
                if (f.delete()) System.out.println("Deleted: " + f.getName());
            }
        }
    }

    // Converts MP3 -> WAV + artwork, skips if already exist
    public void createWavAndArtwork(String mp3FilePath) {
        File mp3File = new File(mp3FilePath);
        String baseName = mp3File.getName().replaceAll("\\.mp3$", "");

        // Ensure WAV
        createWav(mp3FilePath);

        // Ensure Artwork
        File imageFile;
        try {
            AudioFile audioFile = AudioFileIO.read(mp3File);
            Tag tag = audioFile.getTag();
            byte[] imageData = null;

            if (tag != null && tag.getFirstArtwork() != null) {
                Artwork artwork = tag.getFirstArtwork();
                imageData = artwork.getBinaryData();
            }

            if (imageData != null) {
                imageFile = new File(resFolder, baseName + ".png");
                if (imageFile.exists()) {
                    System.out.println("Artwork already exists: " + imageFile.getAbsolutePath());
                } else {
                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                        fos.write(imageData);
                    }
                    System.out.println("Artwork saved at: " + imageFile.getAbsolutePath());
                }
            } else {
                imageFile = new File(resFolder, "default.png");
                if (imageFile.exists()) {
                    System.out.println("Using existing default image: " + imageFile.getAbsolutePath());
                } else {
                    createDefaultArtwork(imageFile);
                    System.out.println("Default placeholder image created at: " + imageFile.getAbsolutePath());
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to extract artwork: " + e.getMessage());
        }
    }

    // Extract only artwork, skip if exists
    public void createArtwork(String mp3FilePath) {
        File mp3File = new File(mp3FilePath);
        String baseName = mp3File.getName().replaceAll("\\.mp3$", "");
        File imageFile;

        try {
            AudioFile audioFile = AudioFileIO.read(mp3File);
            Tag tag = audioFile.getTag();
            byte[] imageData = null;

            if (tag != null && tag.getFirstArtwork() != null) {
                Artwork artwork = tag.getFirstArtwork();
                imageData = artwork.getBinaryData();
            }

            if (imageData != null) {
                imageFile = new File(resFolder, baseName + ".png");
                if (imageFile.exists()) {
                    System.out.println("Artwork already exists: " + imageFile.getAbsolutePath());
                } else {
                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                        fos.write(imageData);
                    }
                    System.out.println("Artwork saved at: " + imageFile.getAbsolutePath());
                }
            } else {
                imageFile = new File(resFolder, "default.png");
                if (imageFile.exists()) {
                    System.out.println("Using existing default image: " + imageFile.getAbsolutePath());
                } else {
                    createDefaultArtwork(imageFile);
                    System.out.println("Default placeholder image created at: " + imageFile.getAbsolutePath());
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to extract artwork: " + e.getMessage());
        }
    }

    // Generate default placeholder artwork
    private void createDefaultArtwork(File imageFile) {
        try {
            BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, 300, 300);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String text = "No Cover";
            FontMetrics fm = g.getFontMetrics();
            int x = (300 - fm.stringWidth(text)) / 2;
            int y = (300 - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, x, y);
            g.dispose();
            ImageIO.write(img, "PNG", imageFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}