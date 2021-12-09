package uet.oop.bomberman.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip stageStart;
    private static Clip bgMusic;
    private static Clip menuMusic;

    public static void playMenuMusic() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/menuMusic.wav"));
            menuMusic = AudioSystem.getClip();
            menuMusic.open(in);
            menuMusic.start();
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMenuMusic() {
        if (menuMusic.isRunning()) {
            menuMusic.stop();
        }
    }

    public static void playStageStart(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/stageStart.wav"));
            stageStart = AudioSystem.getClip();
            stageStart.open(in);
            stageStart.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void playBGMusic(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/bgMusic.wav"));
            bgMusic = AudioSystem.getClip();
            bgMusic.open(in);
            bgMusic.start();
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playSound(String soundName) {
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/" + soundName + ".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}