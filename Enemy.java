import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Enemy 
{
	Rectangle hitbox;
	
	float posX;
	float posY;
	
	boolean isActive;
	int inactivity;
	
	public Enemy()
	{
		Random randomInt = new Random();
		
		this.isActive = false;
		this.inactivity = 200;
		
		this.posX = (int) (randomInt.nextInt(801) + 100);
		this.posY = (int) (randomInt.nextInt(801) + 100);
		
		hitbox = new Rectangle((int) posX, (int) posY, 30, 30);
	}
	
	public void activate()
	{
		if(inactivity-- == 0)
			isActive = true;
	}
	public void deactivate(Queue<Projectile> projectiles)
	{
		Object[] projectilesC = projectiles.toArray();
		
		for(Object projectileC : projectilesC)
		{
			if(isActive && ((Projectile) projectileC).hitbox.intersects(this.hitbox))
			{
				SpielMain.spielFrame.setScore();
				this.isActive = false;
				this.inactivity = 400;
				SpielMain.spielFrame.countdown.setValue(SpielMain.spielFrame.countdown.getValue() + 5);
				return;
			}	
		}
		
	}
	public void update(Player player, LinkedList<Enemy> enemies)
	{
		if(isActive)
		{	
			for(Enemy enemy : enemies)
				if(enemy != this && enemy.isActive && enemy.hitbox.intersects(this.hitbox))
				{
					if(enemy.hitbox.x > this.hitbox.x)
						this.posX += (float) (0 - 10);
					else
						this.posX += (float) (0 + 10);
					
					if(enemy.hitbox.y > this.hitbox.y)
						this.posY += (float) (0 - 10);
					else
						this.posY += (float) (0 + 10);
					
					this.hitbox.x = (int) posX;
					this.hitbox.y = (int) posY;
					
					return;
				}
			
			this.posX = (float) (this.posX + (1/Math.sqrt(Math.pow(player.getXPos() - this.posX, 2) + Math.pow(player.getYPos() - this.posY, 2))) * (player.getXPos() - this.posX));
			this.posY = (float) (this.posY + (1/Math.sqrt(Math.pow(player.getXPos() - this.posX, 2) + Math.pow(player.getYPos() - this.posY, 2))) * (player.getYPos() - this.posY));
		
			this.hitbox.x = (int) posX;
			this.hitbox.y = (int) posY;
			
			
		}
		else
			activate();
	}
}
