import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.Font;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener
{
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private final int DELAY = 0;
    private int w = 1024;
    private int h = 768;	
    private Timer timer;
    private int count = 0;
    JLabel scorelabel = new JLabel("Score = 0");
    JLabel gamelabel = new JLabel("Game Over!");
    public int score=0;
    public static final Color LIGHT_BLUE  = new Color(51,153,255);
    public static final Color GREEN  = new Color(0,204,0);
    public static boolean inGame;
    private int EnemySpeed;
    private int cloudx=w;

    private Player player;
    private Player player2;
    //private ArrayList<Enemy> EnemyList;
    private Image img;
    //private Menu menu;
    public static boolean CanResetGame;
    int wait=0;
    private boolean ReversePositions=false;
    private boolean Player1atRight = false;
    private boolean Player2atRight = true;
    private int HP; // Max HP
    private int Power; // Max Power
    private int dyingcount1 = 240;
    private int dyingcount2 = 240;
    
	
       
    public Board() 
    {    	
        initBoard();
    }
    
    private void initBoard() //Initializes all the game objects
    {	
    	addKeyListener(new TAdapter());
        //setBackground(Color.DARK_GRAY);
        setFocusable(true);
        add(scorelabel);
        add(gamelabel);

        gamelabel.setVisible(false);
        player = Player.getInstance(100, (h/2)+35);
        player2 = Player.getInstance(900,(h/2)+35);
      //  EnemyList = new ArrayList<>();

        inGame = true;
        CanResetGame=false;
        EnemySpeed=6;
        HP = player.getHP();
        Power = player.getPower();
        
       // menu = new Menu();
	
        setPreferredSize(new Dimension((int)w, (int)h));   //Set the size of Window     
        timer = new Timer(DELAY, this); //Timer with 10 ms delay 
        timer.start();
    }
    
    
    @Override
    public void paintComponent(Graphics g) //Draws all the components on screen
    {
    	g.setColor(getBackground());		//get the background color
        g.clearRect(0 , 0, (int)w, (int)h);	//clear the entire window
    	Dimension size = getSize();  //get the current window size  	
        w = (int)size.getWidth();
        h = (int)size.getHeight();

       // super.paintComponent(g);
        //g.drawImage(img, 0, 0, null);
        
        g.setColor(LIGHT_BLUE);
        g.fillRect(0, 0, w, (h/2)+170);
        g.setColor(GREEN);
        g.fillRect(0, (h/2)+170, w, h);
        scorelabel.setForeground(Color.WHITE);
        gamelabel.setForeground(Color.RED);
        
        Graphics2D g2d = (Graphics2D) g;

        if(ReversePositions==true){
            player.ReverseWidth();
            player2.ReverseWidth();
            ReversePositions=false;
        }

        player.paintComponent(g2d);
        player2.paintComponent(g2d);
        //DrawEnemies(g);


        ImageIcon imageicon = new ImageIcon("E:/cloud.png");
        Image cloud = imageicon.getImage();
        g.drawImage(cloud,cloudx,20,250,80,null);
        g.drawImage(cloud,cloudx+250,100,200,80,null);
        g.drawImage(cloud,cloudx+400,20,200,80,null);

        var font = new Font("Serif", Font.BOLD, 18);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("S --> STRIKE",10, 20);

        font = new Font("Serif", Font.BOLD, 18);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("SPACE --> JUMP",10, 50);

        if(inGame==false){
            font = new Font("Serif", Font.BOLD, 40);
            g2d.setColor(Color.RED);
            g2d.setFont(font);
            g2d.drawString("GAME OVER",(w/2)-120, h/2);   
        }

        if(player.getHP()>=0){
        g2d.setColor(Color.GREEN);
        g.fillRect(220, 40, 200, 20);
        g2d.setColor(Color.RED);
        g.fillRect(220+player.getHP(), 40, 200-player.getHP(), 20);
        }
        if(player2.getHP()>=0){
            g2d.setColor(Color.GREEN);
            g.fillRect(580, 40, HP, 20);
            g2d.setColor(Color.RED);
            g.fillRect(580, 40, HP-player2.getHP(), 20);
        }
        g2d.setColor(Color.BLUE);
        g.fillRect(220, 80, player.getPower(), 20);
        g2d.setColor(Color.GRAY);
        g.fillRect(220+player.getPower(), 80, 150-player.getPower(), 20);

        g2d.setColor(Color.BLUE);
        g.fillRect(630, 80, 150, 20);
        g2d.setColor(Color.GRAY);
        g.fillRect(630, 80, Power-player2.getPower(), 20);

        Toolkit.getDefaultToolkit().sync();

    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        step();
        if(inGame==true)
        count++;
        //if(count%13==0)
        //score++;
       // player.DecreaseHP(1);
       // player2.DecreaseHP(1);
      // player.DecreasePower(1);
        player.DecreasePower(1);
        player2.DecreasePower(1);
    }
    public void step(){
     if(inGame==true){
        player.move();
        player.jump();
        player.ChargePower();
        player2.move();
        player2.jump();
        player2.ChargePower();

        
        DyingAnimation(); 
        SwapDirections();
        checkBlockstates();

        cloudx--;
        if(cloudx==-550)
        cloudx=w;
        scorelabel.setText("Score = " + score);
        repaint();
     }
    }
    private class TAdapter extends KeyAdapter 
    {

        @Override
        public void keyReleased(KeyEvent e) 
        {
            if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT|| e.getKeyCode()==KeyEvent.VK_K )
            player2.keyReleased(e);
            else if(e.getKeyCode()==KeyEvent.VK_A || e.getKeyCode()==KeyEvent.VK_D || e.getKeyCode()==KeyEvent.VK_R )
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_M|| e.getKeyCode()==KeyEvent.VK_J|| e.getKeyCode()==KeyEvent.VK_K|| e.getKeyCode()==KeyEvent.VK_N)
            player2.keyPressed(e);
            else if(e.getKeyCode()==KeyEvent.VK_A || e.getKeyCode()==KeyEvent.VK_D || e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_S || e.getKeyCode()==KeyEvent.VK_G|| e.getKeyCode()==KeyEvent.VK_T|| e.getKeyCode()==KeyEvent.VK_R || e.getKeyCode()==KeyEvent.VK_F)
            player.keyPressed(e);
        }
    }
    
   /*
    public void checkCollision(){
        ArrayList<Enemy> enemies = EnemyList;
        for(int i=0; i<enemies.size();i++){  
         if(enemies.get(i).getBounds().intersects(player.getBounds())==true){
             enemies.get(i).SetCanKill(true);
             if(player.CanKill==true && player.Xdirection!=enemies.get(i).Xdirection && player.PiercedEnemy==true ){//player.PiercedEnemy==true
                 enemies.get(i).die(true);
             }
             if(player.CanKill==false && enemies.get(i).CanKill==true && enemies.get(i).PiercedPlayer==true){
                player.die(true);
                if(player.EndGame==true)
                inGame=false;
             }
         }
       }
    } 

    public void IncreaseEnemySpeed(){
     if(score==0)
         EnemySpeed = 6;
     if(score>=100 && score<200)
         EnemySpeed = 8;
     if(score>=200 && score<300)
         EnemySpeed = 10;
     if(score>=300 && score<400)
         EnemySpeed = 12;
     if(score>=400 && score<500)
         EnemySpeed = 14;
     if(score>=500)
         EnemySpeed = 16;
    }
    
    public void DrawEnemies(Graphics g){
        ArrayList<Enemy> enemies = EnemyList;
        Graphics2D g2d = (Graphics2D) g;
       for(int i=0; i<enemies.size();i++){  
       enemies.get(i).paintComponent(g2d);
       }
    }
    
    public void EnemiesMove(){
         ArrayList<Enemy> enemies = EnemyList;
          for(int i=0; i<enemies.size();i++){  
           enemies.get(i).move();
       }
    }
    
    public void dispose(){
         ArrayList<Enemy> enemies = EnemyList;
          for(int i=0; i<enemies.size();i++){  
           if(enemies.isEmpty()==false && enemies.get(i).RemoveEnemy==true){
               enemies.remove(i);
               score+=20;
           }
           if(enemies.isEmpty()==false){
           if(enemies.get(i).Xdirection=='l' && enemies.get(i).getX()<-30 || enemies.get(i).Xdirection=='r' && enemies.get(i).getX()>w+30){
              enemies.remove(i);
              score+=20;
           }
           }
       }
    } */
    
    //NEED TO ADD CONDITION FOR BLOCKING BLAST TOO
    //ALSO BLOCK ONLY WHEN THE OPPONENT CANKILL==TRUE && CANBLAST==TRUE
    public void checkBlockstates(){
        if(Player1atRight==false){
            if(player.Xdirection=='l' && player2.CanKill==true)
                player.setBlockstate(true);
            else if(player2.Xdirection=='r' && player.CanKill==true)
                player2.setBlockstate(true);
            else{
                player.setBlockstate(false);
                player2.setBlockstate(false);
            }
        }
        else if(Player1atRight==true){
            if(player.Xdirection=='r' && player2.CanKill==true)
                player.setBlockstate(true);
            else if(player2.Xdirection=='l' && player.CanKill==true)
                player2.setBlockstate(true);
            else{
                player.setBlockstate(false);
                player2.setBlockstate(false);
            }
        }
        
    }

    public void SwapDirections(){
        if(player.getX()>=player2.getX()-260 && Player1atRight==false){
            Player1atRight = true;
            Player2atRight = false;
            ReversePositions= true;
            player.setX(player.getX()+290);
            player2.setX(player2.getX()-290);
        }
        if(player2.getX()>=player.getX()-260 && Player2atRight==false){
             Player2atRight = true;
             Player1atRight = false;
             ReversePositions = true;
             player2.setX(player2.getX()+290);
             player.setX(player.getX()-290);
        } 
    }

    public void DyingAnimation(){
        if(player.getHP()<=0){
            player.isDead = true;
            if(Player2atRight==true){
                if(player.isDefeated==false){
                    player.setX(player.getX()-8);
                    dyingcount1 = dyingcount1-8;
                }
                if(dyingcount1==0)
                    player.isDefeated=true;
            }
            else{
                if(player.isDefeated==false){
                    player.setX(player.getX()+8);
                    dyingcount1 = dyingcount1 - 8;
                }
                if(dyingcount1==0)
                    player.isDefeated=true;
            }        
        } 
        if(player2.getHP()<=0){
            player2.isDead = true;
            if(Player1atRight==true){
                if(player2.isDefeated==false){
                    player2.setX(player2.getX()-8);
                    dyingcount2 = dyingcount2-8;
                }
                if(dyingcount2==0)
                    player2.isDefeated=true;
            }
            else{
                if(player2.isDefeated==false){
                    player2.setX(player2.getX()+8);
                    dyingcount2 = dyingcount2 - 8;
                }
                if(dyingcount2==0)
                    player2.isDefeated=true;
            }  
        }  
    }
}