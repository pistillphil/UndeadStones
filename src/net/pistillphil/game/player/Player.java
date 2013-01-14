package net.pistillphil.game.player;

import java.util.HashMap;

import net.pistillphil.game.Game;
import net.pistillphil.game.enums.Facing;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;


public class Player
{
	public float x, y;
	private final float SPEED = 0.2f;
	private final int frameLength = 200;
	private int spriteSize;
	private SpriteSheet spriteSheet;
	private HashMap<Facing, Image> sprites;
	private HashMap<Facing, Animation> moveAnimation;
	public Rectangle rect;
	public Facing facing;
	public long lastMove = System.currentTimeMillis();
	public int hitPoints = 5;

	public Player(float x, float y, SpriteSheet spriteSheet, int spriteSize)
	{
		this.x = x;
		this.y = y;
		this.spriteSize = spriteSize;
		this.spriteSheet = spriteSheet;
		this.facing = Facing.DOWN;
		this.setSprites();
		this.setMoveAnimation();

	}

	public void setSprites()
	{
		sprites = new HashMap<Facing, Image>();
		sprites.put(Facing.UP, spriteSheet.getSprite(2, 0));
		sprites.put(Facing.DOWN, spriteSheet.getSprite(0, 0));
		sprites.put(Facing.LEFT, spriteSheet.getSprite(1, 0));
		sprites.put(Facing.RIGHT, spriteSheet.getSprite(3, 0));
		Image temp = sprites.get(Facing.DOWN);
		this.rect = new Rectangle(this.x, this.y, temp.getWidth(),temp.getHeight());
	}

	public void setMoveAnimation()
	{
		moveAnimation = new HashMap<Facing, Animation>();

		Image[] temp = new Image[] { spriteSheet.getSprite(2, 1),
				spriteSheet.getSprite(2, 2), spriteSheet.getSprite(2, 3),
				spriteSheet.getSprite(2, 0) };
		moveAnimation.put(Facing.UP, new Animation(temp, frameLength));

		temp = new Image[] { spriteSheet.getSprite(0, 1),
				spriteSheet.getSprite(0, 2), spriteSheet.getSprite(0, 3),
				spriteSheet.getSprite(0, 0) };
		moveAnimation.put(Facing.DOWN, new Animation(temp, frameLength));

		temp = new Image[] { spriteSheet.getSprite(1, 1),
				spriteSheet.getSprite(1, 2), spriteSheet.getSprite(1, 3),
				spriteSheet.getSprite(1, 0) };
		moveAnimation.put(Facing.LEFT, new Animation(temp, frameLength));

		temp = new Image[] { spriteSheet.getSprite(3, 1),
				spriteSheet.getSprite(3, 2), spriteSheet.getSprite(3, 3),
				spriteSheet.getSprite(3, 0) };
		moveAnimation.put(Facing.RIGHT, new Animation(temp, frameLength));
	}

	public void render()
	{
		if (lastMove + frameLength > System.currentTimeMillis())
		{
			moveAnimation.get(facing).draw(this.x, this.y);
		}

		else
		{
			sprites.get(facing).draw(this.x, this.y);
		}
	}

	public float getX()
	{
		return this.x + (spriteSize / 2);
	}

	public float getY()
	{
		return this.y + (spriteSize / 2);

	}

	public void moveUp(int delta)
	{
		facing = Facing.UP;
		if (!(getY() < 32))
			this.y -= delta * SPEED;
		lastMove = System.currentTimeMillis();
	}

	public void moveDown(int delta)
	{
		facing = Facing.DOWN;
		if (!(getY() > Game.WINDOW_HEIGHT))
			this.y += delta * SPEED;
		lastMove = System.currentTimeMillis();
	}

	public void moveLeft(int delta)
	{
		facing = Facing.LEFT;
		if (!(getX() < 0))
			this.x -= delta * SPEED;
		lastMove = System.currentTimeMillis();
	}

	public void moveRight(int delta)
	{
		facing = Facing.RIGHT;
		if (!(getX() > Game.WINDOW_WIDTH))
			this.x += delta * SPEED;
		lastMove = System.currentTimeMillis();
	}
	
	public void update()
	{
		rect.setX(x);
		rect.setY(y);
		
	}
}
