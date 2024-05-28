package godofstock;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer implements Runnable {
    private volatile boolean isPlaying;

    private FloatControl control;
    private int volumeLevel = 5;
    private final int volumeRate = 10;

    @Override
    public void run() {
        isPlaying = true;

        File music = new File("src/main/resources/main-music.wav");
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(getVolume());

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            while (isPlaying) {
                Thread.sleep(100);
            }
            clip.stop();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        isPlaying = false;
    }

    private float getVolume() {
        float biasedMin = control.getMinimum() + 15.0f; // 실제 최솟값은 너무 작아서 편향값 추가

        return biasedMin + ((control.getMaximum() - biasedMin) / volumeRate * volumeLevel);
    }

    public void volumeUp() {
        if (volumeLevel + 1 <= volumeRate) {
            System.out.printf("현재 볼륨: %d %%\n", ++volumeLevel * volumeRate);
        }
        control.setValue(getVolume());
    }

    public void volumeDown() {
        if (volumeLevel - 1 >= 0) {
            System.out.printf("현재 볼륨: %d %%\n", --volumeLevel * volumeRate);
        }
        control.setValue(getVolume());
    }
}
