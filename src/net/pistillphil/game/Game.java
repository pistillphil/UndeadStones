package net.pistillphil.game;

import net.pistillphil.game.level.LevelState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Game extends StateBasedGame
{
	public static final String NAME = "Undead Stones";
	public static final int FPS = 60;
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 704;
	public static final boolean FULLSCREEN = false;
	public static final String firstLevelName = "level_0";

	public Game()
	{
		super(NAME);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		addState(new LevelState(firstLevelName, WINDOW_WIDTH, WINDOW_HEIGHT));
		enterState(0);

	}

	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Game());

		app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN);
		app.setTargetFrameRate(FPS);
		app.start();

	}
	

}
