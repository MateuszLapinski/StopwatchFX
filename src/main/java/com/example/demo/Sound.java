package com.example.demo;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {

    private Clip musicClip;
    URL url=getClass().getResource("/com/example/demo/ring.wav");


    public void play(){
        try{
            AudioInputStream ais=null;
            if (url != null) {
                ais= AudioSystem.getAudioInputStream(url);
            }

            Clip clip= AudioSystem.getClip();
            musicClip=clip;

            clip.open(ais);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if(event.getType()==LineEvent.Type.STOP){
                        clip.close();
                    }
                }
            });
            ais.close();
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        musicClip.stop();
        musicClip.close();
    }
}
