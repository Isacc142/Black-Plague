import java.awt.Rectangle;
import java.util.LinkedList;

public class Player 
{
	int health;
	//float speed;
	
	private int xPos;
	private int yPos;
	
	Rectangle hitbox;
	
	public Player()
	{
		this.health = 1000;
		//this.speed = 0.01f;
		this.xPos = 100;
		this.yPos = 100;
		hitbox = new Rectangle(100, 100, 30, 30);
	}
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.hitbox.x = xPos;
		this.hitbox.y = yPos;
	}
	
	public void setHealth(LinkedList<Enemy> enemies){
		for(Enemy enemy : enemies)
			if(enemy.hitbox.intersects(this.hitbox))
			{
				this.health -= 1;
				
				SpielMain.spielFrame.setHealth(this.health);
			}
	}
	
	public Rectangle getHitbox(){
		return this.hitbox;
	}
	public float getXPos(){
		return xPos;
	}
	public float getYPos(){
		return yPos;
	}
	
	public void update(boolean bl_up, boolean bl_down, boolean bl_left, boolean bl_right)
	{
		/*if(bl_up || bl_down || bl_left || bl_right)
			this.speed = this.speed < 7.0f ? this.speed + 0.01f : 6.0f;
		else
			this.speed = this.speed > 0.01f ? this.speed - 0.01f : 0.01f;
		*/
		if(bl_up)		this.yPos -= 5;
		if(bl_down)		this.yPos += 5;
		if(bl_left)		this.xPos -= 5;
		if(bl_right)	this.xPos += 5;
		
		
		
		hitbox.x = xPos;
		hitbox.y = yPos;
		
	}
}
