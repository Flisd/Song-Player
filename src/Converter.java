import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Converter {

    private final File wavFolder;

    public Converter() {
        // Set your folder path here
        this.wavFolder = new File("C:\\Users\\Tijil\\OneDrive\\Desktop\\Java projects\\SongPlayer\\WavFolder");
        if (!wavFolder.exists())
            wavFolder.mkdirs();

    }

    // Convert MP3 to WAV inside WavFolder
    public File createWav(String mp3FilePath) {
        try {
            File mp3File = new File(mp3FilePath);
            String baseName = mp3File.getName().replaceAll("\\.mp3$", "");
            File wavFile = new File(wavFolder, baseName + ".wav");

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

    public void clearWav() {
        File[] files = wavFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
        if (files != null) {
            for (File f : files) {
                if (f.delete()) {
                    System.out.println("Deleted: " + f.getName());
                }
            }
        }
    }
}
