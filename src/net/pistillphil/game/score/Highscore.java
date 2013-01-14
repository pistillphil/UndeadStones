package net.pistillphil.game.score;

import net.pistillphil.game.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Highscore
{
	public long score;

	public Highscore()
	{

	}
	
	public void render(Graphics g)
	{
		g.scale(3,3);
		g.setAntiAlias(true);
		Color temp = g.getColor();
		g.setColor(Color.white);
		g.drawString("Score: " + Long.toString(this.score),Game.WINDOW_WIDTH/3 -120,5);
		g.setColor(temp);
		g.scale(1, 1);
		g.setAntiAlias(false);
	}

}
