import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import java.io.File;

public class music {
    
    public void playmusic(){
        try{
          String path = "E:/bg.wav";  
          File music = new File(path);
          if(music.exists()){
              AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
              Clip clip = AudioSystem.getClip();
              clip.open(audioInput);
             // clip.start();
              clip.loop(clip.LOOP_CONTINUOUSLY);
              //JOptionPane.showMessageDialog(null,"press to stop");
          }
          else{
              System.out.println("cant find file");
          }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
      }  
}
