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
    private static Clip gameOver;

    public static void playMenuMusic() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/menuMusic75.wav"));
            menuMusic = AudioSystem.getClip();
            menuMusic.open(in);
            menuMusic.start();
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playStageStart(){
        if (stageStart != null && stageStart.isRunning()) {
            return;
        }
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/stageStart60.wav"));
            stageStart = AudioSystem.getClip();
            stageStart.open(in);
            stageStart.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playBGMusic(){
        if ((bgMusic != null && bgMusic.isRunning())) {
            return;
        }
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/bgMusic80.wav"));
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

    public static void playGameOver() {
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("./res/audio/gameOver60.wav"));
            gameOver = AudioSystem.getClip();
            gameOver.open(in);
            gameOver.start();
            gameOver.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void stopMenuMusic() {
        if (menuMusic != null && menuMusic.isRunning()) {
            menuMusic.stop();
        }
    }

    public static void stopGameOver() {
        if (gameOver != null && gameOver.isRunning()) {
            gameOver.stop();
        }
    }

    public static void stopBGMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop();
        }
    }
}