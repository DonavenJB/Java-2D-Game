package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;

	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		//create HUD object
		SuperObject heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;

	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		//title state
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		//play state
		if(gp.gameState == gp.playState) {
			drawPlayerLife();			
		}
		//pause state
		if(gp.playState == gp.pauseState) {
			//drawPauseScreen();
			drawPlayerLife();
		}	
		//dialogue state
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialoguesScreen();
		}	
	}
	
	public void drawPlayerLife() {
		
		
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//draw max life
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		 x = gp.tileSize/2;
		 y = gp.tileSize/2;
		 i = 0;
		 
		 //draw current life
		 while(i < gp.player.life) {
			 
			 g2.drawImage(heart_half, x, y, null);
			 i++;
			 if(i < gp.player.life) {
				 g2.drawImage(heart_full, x, y, null);
			 }
			 i++;
			 x += gp.tileSize;
		 }
		 
		
	}
	
	public void drawTitleScreen() {
		
		//title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		String text = "Kitten girl Adventures";
		int x = 100;
		int y = gp.tileSize*3;
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//blue boy image
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// Menu 
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "New Game";
		x = 100;
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		
		text = "Load Game";
		x = 100;
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "Quit";
		x = 100;
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	
	public void drawDialoguesScreen() {
		
		//window
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;	
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}	
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
//	public void drawPauseScreen() {
//		String text = "Paused";
//		int x = 0;	
//		int y = 10;
//		g2.drawString(text, x, y);
//		
//	}
	
//	public int getXforCenteredText(String text) {
//		
//		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//		 int x = gp.screenWidth/2 - length/2;
//		return x;
//	}
}
