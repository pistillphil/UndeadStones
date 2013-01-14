package net.pistillphil.game.HUD;

import net.pistillphil.game.Game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class HUD
{
	public int hitPoints;
	private Image heart;
	private int distance;
	private int offset = 64;

	public HUD(int hitPoints) throws SlickException
	{
		this.hitPoints = hitPoints;
		this.heart = new Image("img/heartshield.gif");
		this.heart = this.heart.getScaledCopy(0.6f);
	}

	public void render()
	{
		distance = 0;
		for (int i = 0; i < hitPoints; i++)
		{
			//heart.draw(400,400);
			heart.draw(Game.WINDOW_WIDTH - offset - distance, Game.WINDOW_HEIGHT - this.offset);
			distance += offset;
		}
	}

}
