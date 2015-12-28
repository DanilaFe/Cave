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
	/**
	 * Whether this chunk was loaded previously or not.
	 */
	public boolean wasLoaded = false;

	public Tile getTile(int x, int y){
		return tiles[y * Constants.CHUNK_SIZE / Constants.TILE_SIZE + x];
	}

	public void setLoaded(boolean loaded){
		wasLoaded = isLoaded;
		isLoaded = loaded;
	}

	public void setTile(Tile tile, int x, int y){
		tiles[y * Constants.CHUNK_SIZE / Constants.TILE_SIZE + x] = tile;
		tile.position.set(x, y).scl(Constants.TILE_SIZE);
		tile.myChunk = this;
	}
}
