package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    public void playMusic(String music){
        try {
            String path = System.getProperty("user.dir");
            if(path.startsWith("game", path.length()-4)){
                path = path.substring(0, path.length()-4);
            }
            File f = new File(path+"/sounds/"+music);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
