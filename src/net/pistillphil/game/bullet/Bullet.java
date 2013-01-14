package net.pistillphil.game.bullet;


import net.pistillphil.game.enums.Facing;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;


public class Bullet
{
	private float x,y;
	private final float SPEED = 0.35f;
	private Facing facing;
	private SpriteSheet spriteSheet;
	private Animation animation;
	private final int frameDuration = 100;
	public Rectangle rect;

	public Bullet(float x, float y, Facing facing, SpriteSheet spriteSheet) throws SlickException
	{
		this.x = x;
		this.y = y;
		this.facing = facing;
		this.spriteSheet = spriteSheet;
		setAnimation();

	}
	
	private void setAnimation()
	{
	
		Image temp[] = new Image[]{spriteSheet.getSprite(0, 0),spriteSheet.getSprite(1, 0) ,spriteSheet.getSprite(2, 0) ,spriteSheet.getSprite(3, 0)};
		animation = new Animation(temp,frameDuration);
		rect = new Rectangle(x,y,temp[0].getWidth(),temp[0].getHeight());
		
	}
	
	public void render()
	{
		animation.draw(this.x, this.y);
	}
	
	public void update(int delta)
	{
		rect.setX(x);
		rect.setY(y);
		
		switch(facing){
		case UP:
			this.y -= delta * SPEED;
			break;
		case DOWN:
			this.y += delta * SPEED;
			break;
		case LEFT:
			this.x -= delta* SPEED;
			break;
		case RIGHT:
			this.x += delta*SPEED;
			break;
		}
		
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}

}
