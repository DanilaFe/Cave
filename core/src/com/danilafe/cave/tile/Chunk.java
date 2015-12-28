package com.danilafe.cave.tile;

import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.Constants;
import com.danilafe.cave.Utils;

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

	/**
	 * Gets the tile at the given position
	 * @param x the x-position of the tile
	 * @param y the y-position of the tile
	 * @return the tile at the given position
	 */
	public Tile getTile(int x, int y){
		return tiles[y * Constants.CHUNK_SIZE / Constants.TILE_SIZE + x];
	}

	/**
	 * Sets the isLoaded value, saving the previous one into wasLoaded.
	 * @param loaded the new isLoaded value
	 */
	public void setLoaded(boolean loaded){
		wasLoaded = isLoaded;
		isLoaded = loaded;
	}

	/**
	 * Sets the tile at the given position
	 * @param tile the tile to set
	 * @param x the x-position of the tile
	 * @param y the y-position of the tile
	 */
	public void setTile(Tile tile, int x, int y){
		Tile oldTile = tiles[y * Constants.CHUNK_SIZE / Constants.TILE_SIZE + x];
		if(oldTile != null && isLoaded) Utils.destroyEntityFromTile(oldTile);

		tiles[y * Constants.CHUNK_SIZE / Constants.TILE_SIZE + x] = tile;
		if(tile != null){
			tile.position.set(x, y).scl(Constants.TILE_SIZE);
			tile.myChunk = this;

			if(isLoaded) Utils.createEntityFromTile(tile);
		}
	}
}
