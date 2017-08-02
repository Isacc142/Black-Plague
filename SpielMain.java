package Package1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JFrame;

public class SpielMain 
{
	static SpielFrame spielFrame;
	static Player player;
	static LinkedList<Enemy> enemies;
	static Queue<Projectile> projectiles;
	static Wall wallUp;
	static Wall wallLeft;
	static Wall wallDown;
	static Wall wallRight;
	
	static int overheat = 0;
	static boolean isOverheated = false;
	
	
	public static void main(String[] args) throws InterruptedException
	{
		float xPos;
		float yPos;
		byte xDir;
		byte yDir;
		
		player = new Player();
		wallUp = new Wall(0, 0, 1000, 10); 		//oben
		wallLeft = new Wall(0, 0, 10, 1000);			//links
		wallDown = new Wall(0, 960, 1000, 11);		//unten
		wallRight = new Wall(985, 0, 10, 1000);		//rechts
		projectiles = new LinkedList<Projectile>();
		enemies = new LinkedList<Enemy>();
		spielFrame = new SpielFrame(player, projectiles, wallUp, wallLeft, wallDown, wallRight, enemies);
		
		spielFrame.setSize(1000, 1000);
		spielFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		spielFrame.setResizable(false);
		spielFrame.setLocationRelativeTo(null);
		spielFrame.setVisible(true);
		
		int nextEnemyIn = 300;
		while(true)
		{
			xPos = player.getXPos();
			yPos = player.getYPos();
			
			player.update(spielFrame.isKeyUp(), spielFrame.isKeyDown(), spielFrame.isKeyLeft(), spielFrame.isKeyRight());
			
			while(wallUp.checkCollision(player.getHitbox()))
				player.setPosition((int) xPos, (int) yPos++);
			while(wallLeft.checkCollision(player.getHitbox()))
				player.setPosition((int) xPos++, (int) yPos); 
			while(wallDown.checkCollision(player.getHitbox()))
				player.setPosition((int) xPos, (int) yPos--); 
			while(wallRight.checkCollision(player.getHitbox()))
				player.setPosition((int) xPos--, (int) yPos); 
			
			spielFrame.playerPosition.setText(xPos + " " + yPos);
			
			xDir = 0;
			yDir = 0;
			
			
			if(overheat == 0 & (spielFrame.isKeyW() || spielFrame.isKeyS() || spielFrame.isKeyA() || spielFrame.isKeyD()))
			{
				overheat = 100;
				if(spielFrame.isKeyW() || spielFrame.isKeyS())
					yDir = (byte) (spielFrame.isKeyW() ? -10 : 10);
				if(spielFrame.isKeyA() || spielFrame.isKeyD())
					xDir = (byte) (spielFrame.isKeyA() ? -10 : 10);
					
				spielFrame.setProjectiles(new Projectile(xPos, yPos, xDir, yDir));
			}
			spielFrame.setOverheat((overheat == 0 ? 100 : overheat));
			
			if(overheat > 0)
				--overheat;
				
			
			try
			{
				if(wallUp.checkCollision(projectiles.peek().hitbox) || wallLeft.checkCollision(projectiles.peek().hitbox) || wallUp.checkCollision(projectiles.peek().hitbox) || wallRight.checkCollision(projectiles.peek().hitbox))
					projectiles.remove();
				
			}
			catch(NullPointerException npe){}
			
			try
			{
				if(projectiles.peek().isOutOfBounds())
					projectiles.remove();
				
			}
			catch(NullPointerException npe){}
			
			for(Projectile p : projectiles)
				p.update();
			
			if(--nextEnemyIn == 0)
			{
				nextEnemyIn = 300;
				enemies.add(new Enemy());
			}
			
			
			for(Enemy en : enemies)
			{
				en.update(player, enemies);
				en.deactivate(projectiles);
			}
			
			player.setHealth(enemies);
				
			//if(pj.size() > 10)
				//pj.clear();
			
			spielFrame.repaintScreen();
			spielFrame.setCountdown();
			
			if(player.health <= 0)
				break;
			
			Thread.sleep(8);
		}
	}
}
