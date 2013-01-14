package net.pistillphil.game.enemy;

import java.util.Random;

import net.pistillphil.game.enums.Facing;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;


public class Zombie extends Enemy
{

	private Random rand = new Random();
	private boolean moving;
	private final float SPEED = 0.09f;
	private Point lastPosition;
	private int delta;
	private final int walkDistance = 64;

	public Zombie(float x, float y, SpriteSheet spriteSheet, int spriteSize)
	{
		super(x, y, spriteSheet, spriteSize);
		this.moving = false;
		this.lastPosition = new Point(x, y);
		this.hitPoints = 3;
		setAnimation();
	}

	/*
	 * public float getX() { return this.x +11;
	 * 
	 * }
	 * 
	 * public float getY() { return this.y + 18;
	 * 
	 * }
	 */

	// Determinves the direction the zombie will "jump"
	@Override
	public void move(float playerX, float playerY, int delta)
	{
		this.rect.setX(x);
		this.rect.setY(y);
		this.delta = delta;
		if (!moving)
		{
			if (x < playerX + 32 && x > playerX - 32)
			{
				if (this.y > playerY)
					jump(Facing.UP);
				else
					jump(Facing.DOWN);
			} else if (y < playerY + 32 && y > playerY - 32)
			{
				if (this.x > playerX)
					jump(Facing.LEFT);
				else
					jump(Facing.RIGHT);
			} else
			// if not in the exact lane, it moves x or y direction (never both)

			{
				determineJump(rand.nextInt(2), playerX, playerY);
			}
		}

		else
		{
			moveDirection(); // The zombie moves

			// Checks if the "jump" is completed (the enemy has moves enough)
			if (facing == Facing.UP && lastPosition.getY() - walkDistance >= y)
			{
				moving = false;
				lastPosition.setX(x);
				lastPosition.setY(y);
			}
			if (facing == Facing.DOWN
					&& lastPosition.getY() + walkDistance <= y)
			{
				moving = false;
				lastPosition.setX(x);
				lastPosition.setY(y);
			}
			if (facing == Facing.LEFT
					&& lastPosition.getX() - walkDistance >= x)
			{
				moving = false;
				lastPosition.setX(x);
				lastPosition.setY(y);
			}
			if (facing == Facing.RIGHT
					&& lastPosition.getX() + walkDistance <= x)
			{
				moving = false;
				lastPosition.setX(x);
				lastPosition.setY(y);
			}
		}
		// System.out.print(moving + "\n");
	}

	// switches Facing
	private void jump(Facing direction)
	{

		switch (direction)
		{
		case UP:
			facing = Facing.UP;
			lastPosition.setX(x);
			lastPosition.setY(y);
			moving = true;
			break;
		case DOWN:
			facing = Facing.DOWN;
			lastPosition.setX(x);
			lastPosition.setY(y);
			moving = true;
			break;
		case LEFT:
			facing = Facing.LEFT;
			lastPosition.setX(x);
			lastPosition.setY(y);
			moving = true;
			break;
		case RIGHT:
			facing = Facing.RIGHT;
			lastPosition.setX(x);
			lastPosition.setY(y);
			moving = true;
			break;
		}
	}

	private void determineJump(int xory, float playerX, float playerY)
	{
		if (xory == 0) // moves in x direction
		{
			if (x > playerX)
				jump(Facing.LEFT);
			else
				jump(Facing.RIGHT);

		} else if (xory == 1) // moves in y direction
		{
			if (y > playerY)
				jump(Facing.UP);
			else
				jump(Facing.DOWN);

		}

	}

	// the zombie actually moves
	private void moveDirection()
	{

		switch (facing)
		{
		case UP:
			y -= delta * SPEED;
			break;
		case DOWN:
			y += delta * SPEED;
			break;
		case LEFT:
			x -= delta * SPEED;
			break;
		case RIGHT:
			x += delta * SPEED;
			break;
		}

	}

}
