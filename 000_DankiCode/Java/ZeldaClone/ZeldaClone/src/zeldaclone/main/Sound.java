package zeldaclone.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private Clip clip;
    private AudioInputStream stream;

    private volatile boolean running;

    public Sound(String location) {
        running = false;
        try {
            InputStream inputStream = getClass().getResourceAsStream(location);
            stream = AudioSystem.getAudioInputStream(inputStream);
            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        running = true;
        clip.setFramePosition(0);
        new Thread(new Runnable() {
            public void run() {
                clip.start();
                while (true) {
                    if (clip.getMicrosecondPosition() == clip.getMicrosecondLength())
                        break;
                    if (!running)
                        break;
                }
            }
        }).start();
    }

    public void pause() {
        running = false;
        clip.stop();
    }

    public void resume() {
        running = true;
        new Thread(new Runnable() {
            public void run() {
                clip.start();
                while (true) {
                    if (clip.getMicrosecondPosition() == clip.getMicrosecondLength())
                        break;
                    if (!running)
                        break;
                }
            }
        }).start();
    }

    public void stop() {
        running = false;
        clip.setFramePosition(0);
    }

    public void loop() {
        new Thread(new Runnable() {
            public void run() {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        }).start();
    }

    public boolean isResumed() {
        if (clip.getMicrosecondPosition() > 0)
            return true;
        return false;
    }

}