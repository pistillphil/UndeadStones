package net.pistillphil.game.level;

import net.pistillphil.game.controller.KeyboardControls;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class LevelState extends BasicGameState
{
	private int width, height;
	//private String levelName;
	private Level level;
	private KeyboardControls keyboard;

	public LevelState(String levelName, int width, int height)
	{
		//this.levelName = levelName;
		this.width = width;
		this.height = height;

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		this.level = new Level(width, height);
		level.addCharacter(width / 2, height / 2);
		keyboard = new KeyboardControls(level.players.get(0), level);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, width, height);

		level.render(g);
		if (sbg.isUpdatePaused())
		{
			if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
				sbg.unpauseUpdate();
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		keyboard.handleInput(gc.getInput(), delta, sbg);
		level.moveStuff(delta);
		
		if(gc.getInput().isKeyPressed(Input.KEY_F5))
		{
			sbg.init(gc);
		}
		if(gc.getInput().isKeyPressed(Input.KEY_F10))
		{
			gc.exit();
		}

	}

	@Override
	public int getID()
	{
		return 0;
	}

}
