package com.danilafe.cave.tile;

import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.Constants;

/**
 * A chunk in the game map.
 * @author vanilla
 *
 */
public class Chunk {

	/**
	 * Array of tiles in this chunk
	 */
	public Tile[] tiles = new Tile[Constants.CHUNK_SIZE * Constants.CHUNK_SIZE];
	/**
	 * Position of the chunk in the game world.
	 */
	public Vector2 position = new Vector2(0, 0);
	/**
	 * Whether this chunk is loaded or not.
	 */
	public boolean isLoaded = false;

}
