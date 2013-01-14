package net.pistillphil.game.controller;

import net.pistillphil.game.enums.Facing;
import net.pistillphil.game.level.Level;
import net.pistillphil.game.player.Player;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



public class KeyboardControls
{
	private Player player;
	private Level level;

	public KeyboardControls(Player player, Level level)
	{
		this.player = player;
		this.level = level;

	}

	public void handleInput(Input i, int delta, StateBasedGame sbg)
			throws SlickException
	{
		if (i.isKeyDown(Input.KEY_A))
			player.moveLeft(delta);
		if (i.isKeyDown(Input.KEY_D))
			player.moveRight(delta);
		if (i.isKeyDown(Input.KEY_W))
			player.moveUp(delta);
		if (i.isKeyDown(Input.KEY_S))
			player.moveDown(delta);

		if (i.isKeyDown(Input.KEY_UP))
			level.addBullet(player.x, player.y, Facing.UP);
		else if (i.isKeyDown(Input.KEY_DOWN))
			level.addBullet(player.x, player.y, Facing.DOWN);
		else if (i.isKeyDown(Input.KEY_LEFT))
			level.addBullet(player.x, player.y, Facing.LEFT);
		else if (i.isKeyDown(Input.KEY_RIGHT))
			level.addBullet(player.x, player.y, Facing.RIGHT);

		if (i.isKeyPressed(Input.KEY_ESCAPE))
		{
			if (!sbg.isUpdatePaused())
			{
				sbg.pauseUpdate();
			}
		}

	}

}
