package net.pistillphil.game.level;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.pistillphil.game.Game;
import net.pistillphil.game.HUD.HUD;
import net.pistillphil.game.bullet.Bullet;
import net.pistillphil.game.enemy.Enemy;
import net.pistillphil.game.enemy.Zombie;
import net.pistillphil.game.enums.Facing;
import net.pistillphil.game.enums.SoundType;
import net.pistillphil.game.player.Player;
import net.pistillphil.game.score.Highscore;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;


public class Level
{
	public ArrayList<Player> players;
	public ArrayList<Bullet> bullets;
	ArrayList<Bullet> bulletsToBeRemoved;
	public ArrayList<Enemy> enemies;
	public ArrayList<Enemy> stonedEnemies; // Drugs are bad
	private SpriteSheet bulletSpriteSheet;
	//private final int bulletSpriteSize = 32;
	private SpriteSheet spriteSheet;
	private final int spriteSize = 36;
	private SpriteSheet enemeySpriteSheet;
	private final int enemySpriteSize = 64;
	private long lastBullet = System.currentTimeMillis();
	private final int bulletFreq = 500;
	private int width, height;
	private long lastHit = System.currentTimeMillis();
	private final int hitFreq = 1000;
	private Random randomSpawn;
	private int spawnFreq = 10000;
	private long lastSpawn = System.currentTimeMillis() - spawnFreq / 2;
	private int numStoned = 100;
	private boolean death = false;
	private HashMap<SoundType, Sound> sounds;
	private TiledMap map;
	private HUD hud;
	private Highscore score;

	public Level(int width, int height) throws SlickException
	{
		this.players = new ArrayList<Player>();
		this.bullets = new ArrayList<Bullet>();
		this.enemies = new ArrayList<Enemy>();
		this.stonedEnemies = new ArrayList<Enemy>();
		Image temp = new Image("img/betty_0.png");
		this.spriteSheet = new SpriteSheet(temp, 36, 36, 12, 5);
		temp = new Image("img/plasmaball/plasmaball.png");
		this.bulletSpriteSheet = new SpriteSheet(temp, 32, 32, 30, 15);
		temp = new Image("img/BODY_skeleton.png");
		this.enemeySpriteSheet = new SpriteSheet(temp, 64, 64);
		lastBullet = System.currentTimeMillis();
		this.width = width;
		this.height = height;
		addEnemy(400, 400);
		bulletsToBeRemoved = new ArrayList<Bullet>();
		randomSpawn = new Random();
		setSound();
		map = new TiledMap("map/generic.tmx");
		hud = new HUD(5);
		score = new Highscore();
	}

	public void addCharacter(float x, float y)
	{
		players.add(new Player(x - spriteSize, y - spriteSize, spriteSheet,
				spriteSize));
	}

	public void addBullet(float x, float y, Facing direction) throws SlickException
	{

		if (lastBullet + bulletFreq < System.currentTimeMillis())
		{
			lastBullet = System.currentTimeMillis();
			bullets.add(new Bullet(x, y, direction, bulletSpriteSheet));
			sounds.get(SoundType.Shot).play();
		}
		players.get(0).facing = direction;
	}

	public void addEnemy(float x, float y)
	{
		enemies.add(new Zombie(x, y, enemeySpriteSheet, enemySpriteSize));
	}

	public void render(Graphics g)
	{
		map.render(0, 0);

		for (Enemy e : enemies)
		{
			//g.setColor(Color.green);
			//g.draw(e.rect);
			e.render();
		}

		for (Enemy e : stonedEnemies)
		{
			//g.draw(e.rect);
			e.render();
		}
		for (Bullet b : bullets)
		{
			//g.draw(b.rect);
			b.render();
		}
		for (Player p : players)
		{
			//g.draw(p.rect);
			p.render();
		}
		hud.render();
		score.render(g);
	}

	public void moveStuff(int delta)
	{
		for (Player p : players)
		{
			p.update();
		}
		for (Bullet b : bullets)
		{
			b.update(delta);
			if (b.getX() < 0 - 100 || b.getX() > width + 100)
			{
				bulletsToBeRemoved.add(b);
				//System.out.print(b.getX() + "\n");
			} else if (b.getY() < 0 - 100 || b.getY() > height + 100)
			{
				bulletsToBeRemoved.add(b);
				//System.out.print(b.getY() + "\n");
			}
		}
		for (Bullet b : bulletsToBeRemoved)
		{
			bullets.remove(b);
		}
		bulletsToBeRemoved = new ArrayList<Bullet>();

		for (Enemy e : enemies)
		{
			e.move(players.get(0).x, players.get(0).y, delta);
		}

		handleCollision();

		checkIfStoned();

		spawnEnemies();

	}

	private void handleCollision()
	{
		for (Player p : players)
		{
			for (Enemy e : enemies)
			{
				if (p.rect.intersects(e.rect))
				{
					handlePlayerDamage(p);
				}
			}
			for (Enemy e: stonedEnemies)
			{
				if (p.rect.intersects(e.rect))
				{
					handlePlayerDamage(p);
				}
			}
		}
		for (Bullet b : bullets)
		{
			for (Enemy e : enemies)
			{
				if (b.rect.intersects(e.rect))
				{
					handleEnemyDamage(e);
					sounds.get(SoundType.Enemy).play();
					bulletsToBeRemoved.add(b);
				}
			}
			for(Enemy e: stonedEnemies)
			{
				if (b.rect.intersects(e.rect))
				{
					sounds.get(SoundType.Enemy).play();
					bulletsToBeRemoved.add(b);
				}
			}
		}
	}

	private void handlePlayerDamage(Player player)
	{
		if (lastHit + hitFreq < System.currentTimeMillis())
		{
			sounds.get(SoundType.Self).play();
			player.hitPoints--;
			hud.hitPoints--;
			lastHit = System.currentTimeMillis();
			//System.out.print(player.hitPoints + "\n");

			if (player.hitPoints <= 0)
			{
				death = true;
			}
		}
	}

	private void handleEnemyDamage(Enemy enemy)
	{
		if(!death)
		{
		enemy.hitPoints--;
		score.score +=10;
		}

	}

	private void checkIfStoned()
	{
		for (Enemy e : enemies)
		{
			if (e.hitPoints <= 0)
			{
				sounds.get(SoundType.Stoned).play();
				stonedEnemies.add(e);
				e.stoned = true;
				numStoned++;
				score.score += 20;
			}
		}
		for (Enemy e : stonedEnemies)
		{
			enemies.remove(e);
		}
	}

	private void spawnEnemies()
	{
		if (!death)
		{
			int amount = 0;
			if (lastSpawn + spawnFreq < System.currentTimeMillis()&& (numStoned >= 3 || enemies.size()==0) )
			{
				lastHit = System.currentTimeMillis();	//cannot be hurt if player spawns on top of you
				lastSpawn = System.currentTimeMillis();
				numStoned = 0;
				amount = randomSpawn.nextInt(5) + 1;

				for (int i = 0; i <= amount; i++)
				{
					addEnemy(randomSpawn.nextInt(Game.WINDOW_WIDTH),
							randomSpawn.nextInt(Game.WINDOW_HEIGHT));
				}
			}
		}
		else
			addEnemy(randomSpawn.nextInt(Game.WINDOW_WIDTH),
					randomSpawn.nextInt(Game.WINDOW_HEIGHT));

	}
	private void setSound() throws SlickException
	{
		sounds = new HashMap<SoundType, Sound>();
		sounds.put(SoundType.Shot, new Sound("sound/Shot.wav"));
		sounds.put(SoundType.Enemy, new Sound( "sound/EnemyHurt.wav"));
		sounds.put(SoundType.Self, new Sound("sound/SelfHurt.wav"));
		sounds.put(SoundType.Stoned, new Sound("sound/Stoned.wav"));
	}

}
