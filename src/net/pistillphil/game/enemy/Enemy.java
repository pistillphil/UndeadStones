package net.pistillphil.game.enemy;

import java.util.HashMap;

import net.pistillphil.game.enums.Facing;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;


public abstract class Enemy
{
	protected float x, y;
	protected SpriteSheet spriteSheet;
	protected int spriteSize;
	protected Facing facing;
	private HashMap<Facing, Animation> animation;
	private final int frameLength = 450;
	public Rectangle rect;
	public int hitPoints;
	public boolean stoned;

	public Enemy(float x, float y, SpriteSheet spriteSheet, int spriteSize)
	{
		this.x = x;
		this.y = y;
		this.spriteSheet = spriteSheet;
		this.spriteSize = spriteSize;
		this.facing = Facing.DOWN;
		this.animation = new HashMap<Facing, Animation>();
		stoned = false;
	}

	public void render()
	{
		if (stoned)
			animation.get(facing).stop();
		animation.get(facing).draw(this.x, this.y);

	}

	protected void setAnimation()
	{
		Image[] temp = new Image[] {
				spriteSheet.getSprite(1, 2).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(3, 2).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(5, 2).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(7, 2).getSubImage(14, 14, 36, 50) };
		animation.put(Facing.DOWN, new Animation(temp, frameLength));

		temp = new Image[] {
				spriteSheet.getSprite(1, 0).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(3, 0).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(5, 0).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(7, 0).getSubImage(14, 14, 36, 50) };
		animation.put(Facing.UP, new Animation(temp, frameLength));

		temp = new Image[] {
				spriteSheet.getSprite(1, 1).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(3, 1).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(5, 1).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(7, 1).getSubImage(14, 14, 36, 50) };
		animation.put(Facing.LEFT, new Animation(temp, frameLength));

		temp = new Image[] {
				spriteSheet.getSprite(1, 3).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(3, 3).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(5, 3).getSubImage(14, 14, 36, 50),
				spriteSheet.getSprite(7, 3).getSubImage(14, 14, 36, 50) };
		animation.put(Facing.RIGHT, new Animation(temp, frameLength));
		Image tempImage = spriteSheet.getSprite(1, 3).getSubImage(14, 14, 36,
				50);
		this.rect = new Rectangle(x, y, tempImage.getWidth(),
				tempImage.getHeight());
	}

	public void move(float playerX, float playerY, int delta)
	{

	}

}
