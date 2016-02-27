package com.danilafe.cave.tile;

import com.danilafe.cave.creation.EntityDescriptor;

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
	 * The type of entity that this tile stands for.
	 */
	public EntityDescriptor entityType;

	/**
	 * Creates a new tile from the given properties
	 * @param aparameter the animations of this tile
	 * @return the created TileParameter
	 * @param entityType the type of the entity to create
	 */
	public static TileParameter create(TileAnimation aparameter, EntityDescriptor entityType) {
		TileParameter parameter = new TileParameter();
		parameter.animation = aparameter;
		parameter.entityType = entityType;
		return parameter;
	}

}
