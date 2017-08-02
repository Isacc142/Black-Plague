import java.awt.Rectangle;

public class Projectile
{
	
	
	Rectangle hitbox;
	float xPos;
	float yPos;
	
	byte xDir;
	byte yDir;
	
	
	public Projectile(float xPos, float yPos, byte xDir, byte yDir)
	{
		
		this.xPos = xPos + 10;
		this.yPos = yPos + 10;
		this.xDir = xDir;
		this.yDir = yDir;
			
		this.hitbox = new Rectangle((int) xPos, (int) yPos, 10, 10);
		
		
	
	}
	
	public boolean isOutOfBounds()
	{
		if(hitbox.x > 1100 || hitbox.x < -100)
			return true;
		if(hitbox.y > 1100 || hitbox.y < -100)
			return true;
		
		return false;
	}
	
	public Rectangle getHitbox(){
		return this.hitbox;
	}
	
	public void update()
	{
		xPos += xDir;
		yPos += yDir;
			
		this.hitbox.x = (int) this.xPos;
		this.hitbox.y = (int) this.yPos;

	}
}
