import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Player
{
    private static Player player;
    private int x,y,w,h;
    private int JUMP_SPEED;
    private int MOVE_SPEED;
    private int MIN_JUMP;
    private int OriginalY;
    private boolean CanJump;
    protected boolean CanKill;
    protected boolean CanTeleport;
    protected boolean CanBlast;
    protected boolean isDead;
    protected boolean isDefeated;
    private boolean CanWalk;
    protected boolean PiercedEnemy;
    protected boolean EndGame;
    protected boolean CanCharge;
    protected boolean CanBlock;
    private char Ydirection;
    protected char Xdirection;
    protected Image WalkForwardImage;
    protected Image WalkBackwardImage;
    protected Image[] StrikeImages;
    protected Image StandImage;
    protected Image[] DieImages;
    protected Image DeadImage;
    protected Image DefeatImage;
    protected Image[] ChargeImages;
    protected Image[] BlockImages;
    protected Image[] BlastImages;
    protected Image Teleport0image, Teleport1image;
    protected long Walkcount = 0;
    protected long Strikecount = 0;
    protected long Standcount = 0;
    protected long Diecount = 0;
    protected long Chargecount = 0;
    protected int rand = 0;
    int num=0;
    private int HP;
    private int Power;
    private static int instanceCount = 0;
    private int type;
    private int teleportcount = 0;
    private int BlastCount = 0;
    int Blastdivisor = 4;

	private Player(int x, int y)
	{
        // if player instance 2 then call ReverseWidth()
	this.x = x;
    this.y = y;	
    HP = 200;
    Power = 150;
    CanJump=false;
    CanKill=false;
    CanWalk=false;
    CanTeleport=false;
    CanCharge = false;
    CanBlock=false;
    CanBlast = false;
    isDead=false;
    EndGame=false;
    PiercedEnemy=false;
    JUMP_SPEED=-10;
    MOVE_SPEED = 7;
    OriginalY = y;
    MIN_JUMP = OriginalY - 300;
    Ydirection = 'u';
    Xdirection = 'r';
    //WalkImages;
    StrikeImages = new Image[13];
    //StandImages = new Image[8];
    DieImages = new Image[8];
    ChargeImages = new Image[8];
    BlockImages = new Image[2];
    BlastImages = new Image[4];
    if(instanceCount==1)
    type = 1;
    else
    type = 2;

		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuMove/gokuforward.png");
		WalkForwardImage = imageIcon.getImage();	
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuMove/gokubackward.png");
		WalkBackwardImage = imageIcon.getImage();	
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuMove/gokustand.png");
        //imageIcon = new ImageIcon("E:/haha.png");
		StandImage = imageIcon.getImage();		
        int count = 10;	
    for(int i=0; i<11; i++) // Iterating between 3 images for each aircraft
		{
		imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuFight/" + i + ".png" );
		StrikeImages[count] = imageIcon.getImage();
        count--;
		}		
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuFight/11.png");
		StrikeImages[11] = imageIcon.getImage();
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuFight/12.png" );
		StrikeImages[12] = imageIcon.getImage();

    for(int i=0; i<3; i++) // Iterating between 3 images for each aircraft
		{
	    imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuCharge" + i + ".png" );
		ChargeImages[i] = imageIcon.getImage();
		}	
    for(int i=0; i<=3; i++) // Iterating between 3 images for each aircraft
		{
		imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuBlast" + i + ".png" );
		BlastImages[i] = imageIcon.getImage();
		}		
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuDead.png");
		DeadImage = imageIcon.getImage();	
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuDefeated.png");
        DefeatImage = imageIcon.getImage();
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuTeleport0.png");
		Teleport0image = imageIcon.getImage();	
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuTeleport1.png");
        Teleport1image = imageIcon.getImage();
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuBlock0.png");
        BlockImages[0] = imageIcon.getImage();
        imageIcon = new ImageIcon("E:/GamesAndStuff/Fight/gokuBlock1.png");
        BlockImages[1] = imageIcon.getImage();


		w = WalkForwardImage.getWidth(null)+20;
        h = WalkForwardImage.getHeight(null)+31;
        
        if(instanceCount==2)
        ReverseWidth();
	}
	
        
    public static Player getInstance( int x, int y){
            if(player==null){
                instanceCount++;
                return new Player(x,y);
            }
            else if(instanceCount==1){
                instanceCount++;
                return new Player(x,y);
            }
            else{
                return player;
            }
    }
	public void jump()
	{
        if(y==OriginalY && Ydirection=='d'){
        Ydirection='u';
        CanJump=false;
        }
        if(CanJump==true && y<=MIN_JUMP)
        Ydirection='d';
        if(CanJump==true && Ydirection=='u')
        y+=JUMP_SPEED;
        if(CanJump==true && Ydirection=='d')
        y-=JUMP_SPEED;
        
    }

    public void move()
    {
        if(CanWalk==true && Xdirection=='r' && CanKill==false && CanBlock==false)
        x+=MOVE_SPEED;
        if(CanWalk==true && Xdirection=='l' && CanKill==false && CanBlock==false)
        x-=MOVE_SPEED;
    }
   
    public void die(boolean state)
    {
     if(state==true){
     isDead=true;
     }   
     else
     isDead=false;
    }
	
	public void keyPressed(KeyEvent e) 
	{
        int key = e.getKeyCode();

    if(type==1){
        if(isDead==false){
            //CHANGE THIS AND BELOW THIS ONE IF WANT TO ATTACK IN AIR
            if (key == KeyEvent.VK_W && CanBlast==false && CanCharge==false && CanKill==false) {
                CanJump=true;
                CanKill=false; 
            }
            if (key == KeyEvent.VK_G && CanTeleport==false && CanBlast==false && CanKill==false && CanJump==false) {
                CanKill=true;
                CanBlock=false;
                CanWalk = false;
                CanCharge=false;
                CanJump=false;
                rand = 0;
            }
            if (key == KeyEvent.VK_D && CanKill==false && CanBlast==false){
                CanWalk = true;
                Xdirection = 'r';
            }
            if (key == KeyEvent.VK_A && CanKill==false && CanBlast==false ){
                CanWalk = true;   
                Xdirection = 'l';
            }
            if (key == KeyEvent.VK_T && CanKill==false &&CanBlast==false ){
                CanWalk = false;   
                CanTeleport = true;
            }
            if (key == KeyEvent.VK_R && CanKill==false &&CanJump==false && CanBlast==false){
                CanWalk = false;   
                CanCharge = true;
            }
            if (key == KeyEvent.VK_F && CanKill==false &&CanJump==false){
                CanWalk = false;   
                CanBlast= true;
                CanTeleport=false;
                CanCharge=false;
                rand = 0;
            }
    }
    }
    else{
        if(isDead==false){
            if (key == KeyEvent.VK_UP && CanBlast==false && CanCharge==false && CanKill==false) {
             CanJump=true;
             CanKill=false; 
            }
            if (key == KeyEvent.VK_M && CanTeleport==false && CanBlast==false && CanKill==false && CanJump==false){
                CanKill=true;
                CanBlock=false;
                CanWalk = false;
                CanCharge=false;
                CanJump=false;
                rand = 0;
            }
            if (key == KeyEvent.VK_RIGHT && CanKill==false && CanBlast==false){
             CanWalk = true;
             Xdirection = 'r';
            }
            if (key == KeyEvent.VK_LEFT && CanKill==false &&CanBlast==false ){
             CanWalk = true;   
             Xdirection = 'l';
            }
            if (key == KeyEvent.VK_J && CanKill==false &&CanBlast==false){
                CanWalk = false;   
                CanTeleport = true;
               }
            if (key == KeyEvent.VK_K && CanKill==false &&CanJump==false && CanBlast==false){
                CanWalk = false;   
                CanCharge = true;
            }
            if (key == KeyEvent.VK_N && CanKill==false &&CanJump==false){
                CanWalk = false;   
                CanBlast= true;
                CanCharge=false;
                CanTeleport=false;
                rand = 0;
            }
         }
    }
    }
    
    public void keyReleased(KeyEvent e) 
	{
        int key = e.getKeyCode();

        if(type==2){
            if (key == KeyEvent.VK_RIGHT){
                CanWalk = false;
                CanBlock = false;
                Xdirection = 's';
            }
            if (key == KeyEvent.VK_LEFT){
                CanWalk = false;  
                CanBlock = false;
                Xdirection = 's';
            }
            if (key == KeyEvent.VK_K){
                CanCharge = false;  
            }
        }
        else{
            if (key == KeyEvent.VK_D){
               CanWalk = false;
               CanBlock = false;
               Xdirection = 's';
            }
            if (key == KeyEvent.VK_A){
               CanWalk = false;  
               CanBlock = false;
               Xdirection = 's';
            } 
            if (key == KeyEvent.VK_R){
                CanCharge = false;  
            }  
        }
    }


    public void paintComponent(Graphics2D g) 
	{
    
    if(isDead==false){
    
    if(CanWalk==true && CanKill==false){
        if(Xdirection=='r'){
            if(CanBlock==false)
                g.drawImage(WalkForwardImage, x + w, y, w, h, null);
            else{
                if(w>=0)
                g.drawImage(BlockImages[1],x+w,y,w-10,h-10,null);
                else
                g.drawImage(BlockImages[1],x+w,y,w+5,h-10,null);
            } 
        }
        else{
            if(CanBlock==false)
                g.drawImage(WalkBackwardImage, x + w, y, w, h, null);
            else{
                if(w>=0)
                g.drawImage(BlockImages[0],x+w,y,w-10,h-10,null);
                else
                g.drawImage(BlockImages[0],x+w,y,w+5,h-10,null);
            }

        }
    }
    if(CanWalk==false && CanKill==true && CanTeleport==false && CanBlast==false && CanJump==false){
        if(rand%11==0){
            num = (int)(Strikecount%13);
            Strikecount++;
            }
            rand++;
           
            
            if(w>=0){
                 if(num==0 || num==1 || num==8 || num==9)
                     g.drawImage(StrikeImages[num], x + w+10, y, w-25,h-10, null); //this one for 0,1,8,9
                 else if(num==2 || num==3 || num==10)
                     g.drawImage(StrikeImages[num], x + w+10, y, w-5,h-5, null); //this one for 2,3,10
                 else
                     g.drawImage(StrikeImages[num], x + w+10, y, w,h, null); //this one for 4,5,6,7,11,12
            }
            else{
                if(num==0 || num==1 || num==8 || num==9)
                     g.drawImage(StrikeImages[num], x + w-10, y, w+25,h-10, null); //this one for 0,1,8,9
                 else if(num==2 || num==3 || num==10)
                     g.drawImage(StrikeImages[num], x + w-10, y, w+5,h-5, null); //this one for 2,3,10
                 else
                     g.drawImage(StrikeImages[num], x + w-10, y, w,h, null); //this one for 4,5,6,7,11,12
            }
            if(num==4)
            PiercedEnemy=true;
            if(num==12 && rand%11==0){  
            CanKill=false;
            PiercedEnemy=false;
            num=0;
            }
    }
    if(CanWalk==false && CanKill==false && CanTeleport==false && CanCharge==false && CanBlast==false){

            g.drawImage(StandImage, x + w, y, w, h, null);  
    }
    if(CanWalk==false && CanTeleport==true && CanKill==false){
        //int RandX = (int)(Math.random()*600);
        if(rand%3==0){
            teleportcount++;
            }
            rand++;
        if(teleportcount==0)
        g.drawImage(Teleport0image, x+w, y, w,h,null);
        if(teleportcount==1)
        g.drawImage(Teleport1image, x+w, y, w,h,null);
        if(teleportcount==2){
        this.setX((int)(Math.random()*600));
        //this.setX(RandX);
        g.drawImage(Teleport1image, x+w, y, w,h,null);
        }
        if(teleportcount==4){
        g.drawImage(Teleport0image, x+w, y, w,h,null);
        }
        if(teleportcount==5){    
        CanTeleport=false;
        teleportcount=0;
        rand = 0;
        }
    }
    if(CanWalk==false && CanCharge==true && CanBlast==false){
        if(rand%4==0){
            num = (int)(Chargecount%3);
            Chargecount++;
            }
            rand++;
            if(w>=0){
            g.drawImage(ChargeImages[num],x+w-30,y-25,w+60,h+25,null);
            }
            else{
            g.drawImage(ChargeImages[num],x+w+30,y-25,w-60,h+25,null);
            }
    }
    if(CanWalk==false && CanBlast==true && CanCharge==false){
        if(rand%Blastdivisor==0){
            num = (int)(BlastCount%4);
            BlastCount++;
            }
            rand++;

           if(num==0 && BlastCount==1){ 
            if(w>=0)
            g.drawImage(BlastImages[0], x+w, y,w-10,h-10,null); //FOR IMAGE0
            else
            g.drawImage(BlastImages[0], x+w, y,w,h-10,null); //FOR IMAGE0
            Blastdivisor = 16;
           }
           else if(BlastCount>1){
            Blastdivisor = 4;
            if(w>=0)
            g.drawImage(BlastImages[1], x+w+10, y-10,w+80,h+5,null); //FOR IMAGE1
            else
            g.drawImage(BlastImages[1], x+w-10, y-10,w-80,h+5,null); //FOR IMAGE1

                for(int i =2;i<=BlastCount;i++){

                    if(w>=0){
                        if(i==2)
                        g.drawImage(BlastImages[2], x+(i*w)+10+80, y+36,w-40,h-80,null); //FOR IMAGE2
                        else 
                        g.drawImage(BlastImages[2], x+(i)*(w-40)+170, y+36,w-40,h-80,null); //FOR IMAGE2
                        if(i==BlastCount)
                        g.drawImage(BlastImages[3],x+(i)*(w-40)+210,y+5,w+50,h-30,null); 
                    }
                    else{
                        if(i==2)
                        g.drawImage(BlastImages[2], x+(i*w)-10-80, y+36,w+40,h-80,null); //FOR IMAGE2
                        else 
                        g.drawImage(BlastImages[2], x+(i)*(w+40)-170, y+36,w+40,h-80,null); //FOR IMAGE2
                        if(i==BlastCount)
                        g.drawImage(BlastImages[3],x+(i)*(w+40)-210,y+5,w-50,h-30,null); 
                    }
                    if(i==18){
                       setBlaststate(false);
                    }
                }
           } 
          
           //BLAST COUNT ==0 WHEN CANBLAST==FALSE IN THE END
           
    }
    
    }
    if(isDead==true){
           if(Xdirection=='r' || Xdirection=='l' || Xdirection == 's'){
            if(isDefeated==false)
            g.drawImage(DeadImage, x + w, y, w, h, null); 
            else
            g.drawImage(DefeatImage, x+w, y, w,h,null);
           }
    }
	}
    
    public void wait(int count){
        count--;
        if(count==0)
        return;
    }
    public void setX(int x){
    this.x = x;
    }
    public int getX(){
    return x;
    }
    public int getY(){
        return y;
    }

       public Rectangle getBounds() {
	        return new Rectangle(x + w + 20, y+30, w-50, h-20);
	}
    public void setJumpstate(boolean state){
        CanJump=state;
    }
    public boolean getJumpstate(){
        return CanJump;
    }
    public int getHP(){
        return HP;
    }
    public int getPower(){
        return Power;
    }
    public int DecreaseHP(int decrease){
        if(HP>0)
        HP = HP - decrease;
        return HP;
    }
    public int DecreasePower(int decrease){
        if(Power>0)
        Power = Power - decrease;
        return Power;
    }
    public int IncreasePower(int increase){
        if(Power<150)
        Power = Power + increase;
        return Power;
    }
    public void ChargePower(){
        if(CanCharge==true){
            IncreasePower(2);
        }
    }
    public void setBlockstate(boolean state){
        CanBlock = state;
    }
    public boolean getBlockstate(){
        return CanBlock;
    }

    public void ReverseWidth(){
        Image tempimage = WalkBackwardImage;
        WalkBackwardImage = WalkForwardImage;
        WalkForwardImage = tempimage;
        w = -w;
    }   
    public void setBlaststate(boolean state){
        CanBlast = state;
        BlastCount = 0;
    }
}

