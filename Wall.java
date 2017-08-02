import java.awt.Point;
import java.awt.Rectangle;

public class Wall 
{
	Rectangle hitbox;
	
	public Wall(int xPos, int yPos, int width, int height) 
	{
		hitbox = new Rectangle(xPos, yPos, width, height);
	}
	
	public boolean checkCollision(Rectangle object)
	{
		return this.hitbox.intersects(object);
	}
}
