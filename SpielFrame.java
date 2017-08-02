package Package1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class SpielFrame extends JFrame
{
	private Screen screen;
	JPanel playerStats;
	JLabel score;
	JLabel playerPosition;
	JProgressBar countdown;
	JProgressBar overheat;
	JProgressBar health;
	Player player;
	Queue<Projectile> projectiles;
	Wall wallUp;
	Wall wallLeft;
	Wall wallDown;
	Wall wallRight;
	LinkedList<Enemy> enemies;
	
	private boolean keyUp = false;
	private boolean keyDown = false;
	private boolean keyLeft = false;
	private boolean keyRight = false;
	
	private boolean keyW = false;
	private boolean keyS = false;
	private boolean keyA = false;
	private boolean keyD = false;
	
	private boolean wallIsMoving = false;
	private byte wallMovingCounter = 0;
	
	
	public SpielFrame(Player player, Queue<Projectile> projectiles, Wall wall0, Wall wall1, Wall wall2, Wall wall3, LinkedList<Enemy> enemies)
	{
		super("SpielTest");
		screen = new Screen();
		screen.setBounds(0, 0, 1000, 1000);
		screen.setLayout(null);
		add(screen);
		addKeyListener(new KeyHandler());
		setLayout(null);
		setVisible(true);
		this.player = player;
		this.projectiles = projectiles;
		this.enemies = enemies;
		
		this.wallUp = wall0;
		this.wallLeft = wall1;
		this.wallDown = wall2;
		this.wallRight = wall3;
		
		playerStats = new JPanel();
		playerStats.setBounds(40, 40, 300, 300);
		playerStats.setLayout(null);
		add(playerStats);
		
		playerPosition = new JLabel("hallo");
		playerPosition.setBounds(0, 0, 100, 20);
		playerStats.add(playerPosition);
		
		score = new JLabel("0");
		score.setBounds(0, 30, 100, 20);
		playerStats.add(score);
		
		health = new JProgressBar();
		health.setBounds(0, 60, 100, 20);
		health.setForeground(Color.GREEN);
		health.setMaximum(1000);
		health.setMinimum(0);
		health.setValue(1000);
		playerStats.add(health);
		
		overheat = new JProgressBar();
		overheat.setBounds(0, 90, 100, 20);
		overheat.setForeground(Color.YELLOW);
		overheat.setMaximum(100);
		overheat.setMinimum(0);
		overheat.setValue(0);
		playerStats.add(overheat);
		
		countdown = new JProgressBar();
		countdown.setBounds(0, 120, 100, 20);
		countdown.setForeground(Color.BLUE);
		countdown.setMaximum(1000);
		countdown.setMinimum(0);
		countdown.setValue(1000);
		playerStats.add(countdown);
		
		
	}
	
	public void repaintScreen()
	{
		screen.repaint();
	}
	
	public void setWallUp(){
		this.wallUp.hitbox.y += 1;
	}
	public void setWallLeft(){
		this.wallLeft.hitbox.x += 1;
	}
	public void setWallDown(){
		this.wallDown.hitbox.y -= 1;
	}
	public void setWallRight(){
		this.wallRight.hitbox.x -= 1;
	}
	
	public boolean isKeyUp() {
		return keyUp;
	}
	public boolean isKeyDown() {
		return keyDown;
	}
	public boolean isKeyLeft() {
		return keyLeft;
	}
	public boolean isKeyRight() {
		return keyRight;
	}
	public boolean isKeyW() {
		return keyW;
	}
	public boolean isKeyS() {
		return keyS;
	}
	public boolean isKeyA() {
		return keyA;
	}
	public boolean isKeyD() {
		return keyD;
	}
	
	public void setScore(){
		score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
	}
	
	public void setOverheat(int n){
		this.overheat.setValue(n);
	}
	public void setHealth(int n){
		if(n >= 0)
			this.health.setValue(n);
		else
			this.health.setValue(1000);
	}
	public void setCountdown()
	{
		if(this.wallIsMoving)
		{
			if(this.wallMovingCounter++ < 20)
			{
				this.wallUp.hitbox.y += 1;
				this.wallLeft.hitbox.x += 1;
				this.wallDown.hitbox.y -= 1;
				this.wallRight.hitbox.x -= 1;
			}
			else
			{
				this.wallIsMoving = false;
				this.wallMovingCounter = 0;
				this.countdown.setValue(1000);
			}
			
		}
		else
		{
			if(countdown.getValue() > 0)
				this.countdown.setValue(countdown.getValue() - 1);
				
			else
				this.wallIsMoving = true;
		}
	}

	@SuppressWarnings("serial")
	private class Screen extends JLabel
	{
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.RED);
			g.fillRect((int) player.getHitbox().getX(), (int) player.getHitbox().getY(), (int) player.getHitbox().getWidth(), (int) player.getHitbox().getHeight());
			
			g.setColor(Color.BLUE);
			if(!projectiles.isEmpty())
				for(Projectile projectile : projectiles)
					g.fillRect((int) projectile.getHitbox().getX(), (int) projectile.getHitbox().getY(), (int) projectile.getHitbox().getWidth(), (int) projectile.getHitbox().getHeight());
			
			g.setColor(Color.BLUE);
			g.fillRect(wallUp.hitbox.x, wallUp.hitbox.y, wallUp.hitbox.width, wallUp.hitbox.height);
			g.fillRect(wallLeft.hitbox.x, wallLeft.hitbox.y, wallLeft.hitbox.width, wallLeft.hitbox.height);
			g.fillRect(wallDown.hitbox.x, wallDown.hitbox.y, wallDown.hitbox.width, wallDown.hitbox.height);
			g.fillRect(wallRight.hitbox.x, wallRight.hitbox.y, wallRight.hitbox.width, wallRight.hitbox.height);
			
			if(!enemies.isEmpty())
				for(Enemy enemy : enemies)
				{
					if(enemy.isActive)
						g.setColor(Color.BLACK);
					else
						g.setColor(Color.GRAY);
					
					g.fillRect(enemy.hitbox.x, enemy.hitbox.y, enemy.hitbox.width, enemy.hitbox.height);
				}
		}
	}
	
	public void setProjectiles(Projectile projectile){
		this.projectiles.add(projectile);
	}
	
	private class KeyHandler implements KeyListener
	{
		public void keyPressed(KeyEvent ke)
		{
			if(ke.getKeyCode() == KeyEvent.VK_UP)		keyUp = true;
			if(ke.getKeyCode() == KeyEvent.VK_DOWN)		keyDown = true;
			if(ke.getKeyCode() == KeyEvent.VK_LEFT)		keyLeft = true;
			if(ke.getKeyCode() == KeyEvent.VK_RIGHT)	keyRight = true;
			
			if(ke.getKeyCode() == KeyEvent.VK_W)		keyW = true;
			if(ke.getKeyCode() == KeyEvent.VK_S)		keyS = true;
			if(ke.getKeyCode() == KeyEvent.VK_A)		keyA = true;
			if(ke.getKeyCode() == KeyEvent.VK_D)		keyD = true;
		}

		public void keyReleased(KeyEvent ke)
		{
			if(ke.getKeyCode() == KeyEvent.VK_UP)		keyUp = false;
			if(ke.getKeyCode() == KeyEvent.VK_DOWN)		keyDown = false;
			if(ke.getKeyCode() == KeyEvent.VK_LEFT)		keyLeft = false;
			if(ke.getKeyCode() == KeyEvent.VK_RIGHT)	keyRight = false;
			
			if(ke.getKeyCode() == KeyEvent.VK_W)		keyW = false;
			if(ke.getKeyCode() == KeyEvent.VK_S)		keyS = false;
			if(ke.getKeyCode() == KeyEvent.VK_A)		keyA = false;
			if(ke.getKeyCode() == KeyEvent.VK_D)		keyD = false;
		}

		public void keyTyped(KeyEvent ke){}
		
	}
}
