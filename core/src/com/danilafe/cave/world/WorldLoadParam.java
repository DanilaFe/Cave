package com.danilafe.cave.world;

import com.badlogic.gdx.math.Vector2;

/**
 * Class representing parameters used when a world is loaded
 * @author vanilla
 *
 */
public class WorldLoadParam {

	/**
	 * The level data contain in the given world
	 */
	public LevelData levelData;
	/**
	 * The level manager used to load the the world
	 */
	public LevelManager levelManager;
	/**
	 * The position of the player
	 */
	public Vector2 playerPosition = new Vector2(0,0);

}
