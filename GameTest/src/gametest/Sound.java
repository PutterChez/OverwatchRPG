/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author DELL
 */
public class Sound {

    File src;
    Clip clip;

    Sound(){}
    
    public void setSoundDirectory(String url) {
        src = new File(url);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(src));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
    
    public void playLoop(){
        clip.setLoopPoints(0, -1);
        clip.loop(10);
    }
    
    public void setVolume(int e)
    {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(e);
    }

}
