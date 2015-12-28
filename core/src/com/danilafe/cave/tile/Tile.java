package com.danilafe.cave.tile;

import com.badlogic.gdx.math.Vector2;

/**
 * Tile data structure holding data for a specific tile
 * @author vanilla
 *
 */
public class Tile {

	/**
	 * The parameter of this tile
	 */
	public TileParameter tileParameter;
	/**
	 * The variation of this tile
	 */
	public int tileVariation;
	/**
	 * The chunk this tile is in
	 */
	public Chunk myChunk;
	/**
	 * The rotation of this tile
	 */
	public int rotation;
	/**
	 * Position of the tile in the chunk.
	 */
	public Vector2 position = new Vector2(0, 0);

	/**
	 * Creates a new tile from the given data
	 * @param tileParam the parameter of the tile
	 * @param tileVariation the variation of this tile
	 * @return the created tile.
	 */
	public static Tile create(TileParameter tileParam, int tileVariation){
		Tile newtile = new Tile();
		newtile.tileParameter = tileParam;
		newtile.tileVariation = tileVariation;

		return newtile;
	}

}
