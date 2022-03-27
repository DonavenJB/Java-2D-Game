package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//screen settings
    final int originalTileSize = 16; // 16X16 tiles
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48X48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    
    //world setting 
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
   
    //fps
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    
    //entity and object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    
    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    
    
    
    
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame() {
    	
    	aSetter.setObject();
    	aSetter.setNPC();
    	//playMusic(0);
    	gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;  //0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null) {


       //1 UPDATE information such as char position
            update();
       //draw screen with updated info
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

               nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void update() {

    	
    	if(gameState == playState) {
    		//player
    		player.update();
    		//npc
    		for(int i = 0; i < npc.length; i++) {
    			if(npc[i] != null) {
    				npc[i].update();
    			}
    		}
    	}
    	if(gameState == pauseState) {
    		//nothing
    		
    		System.out.println("working");
    		
    	}
        
     }

    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        
        //debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
        	drawStart = System.nanoTime();
        }
        //title screen
        if(gameState == titleState) {
        	ui.draw(g2);
        }
        //other
        else {
        	//tile
        tileM.draw(g2);

        //object
        for(int i = 0; i < obj.length; i++) {
        	if(obj[i] != null) {
        		obj[i].draw(g2, this);
        	}
        }
        
        //NPC
        for(int i = 0; i < npc.length; i++) {
        	if(npc[i] != null) {
        		npc[i].draw(g2);

        	}
        }
        //player
        player.draw(g2);
        
        //UI
        ui.draw(g2);
        }
        
        
        
        //debug
        if(keyH.checkDrawTime == true) {
        	long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString("Draw Time:" + passed, 10, 400);
        System.out.println("Draw Time:" + passed);
        }
        
        //pauseState temporary  "PAUSED" display, bug in UI drawPauseScreen method
        if(gameState == pauseState) {
        	
        	g2.setColor(Color.white);
    		g2.drawString("PAUSED", 100, 400);
        }
           

        g2.dispose();
    }
    
    public void playMusic(int i) {
    	
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	music.stop();
    }
    
    public void playSE(int i) {
    	se.setFile(i);
    	se.play();
    }

}
