import java.awt.EventQueue;
import javax.swing.JFrame;
    
public class Application extends JFrame {
        
    public Application() {
    
        initUI();
    }
    
    private void initUI() {
    
        add(new Board());

        setSize(1024, 768);
        setResizable(false);
        setTitle("Fight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        }    
        
    public static void main(String[] args) {
            
        EventQueue.invokeLater(() -> {
        Application ex = new Application();
        ex.setVisible(true);
        music m = new music();
        m.playmusic();
            });
        }   
    }
