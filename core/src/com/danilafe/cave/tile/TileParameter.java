package com.danilafe.cave.tile;

/**
 * Parameter of a tile
 * @author vanilla
 *
 */
public class TileParameter {

	/**
	 * The animation of this tile
	 */
	public TileAnimation animation;

	/**
	 * Creates a new tile from the given properties
	 * @param aparameter the animations of this tile
	 * @return the created TileParameter
	 */
	public static TileParameter create(TileAnimation aparameter) {
		TileParameter parameter = new TileParameter();
		parameter.animation = aparameter;
		return parameter;
	}

}
