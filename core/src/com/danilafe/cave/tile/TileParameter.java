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
	public String entityType;

	/**
	 * Creates a new tile from the given properties
	 * @param aparameter the animations of this tile
	 * @return the created TileParameter
	 */
	public static TileParameter create(TileAnimation aparameter, String entityType) {
		TileParameter parameter = new TileParameter();
		parameter.animation = aparameter;
		parameter.entityType = entityType;
		return parameter;
	}

}
