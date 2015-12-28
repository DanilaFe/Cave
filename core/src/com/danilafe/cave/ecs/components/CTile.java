package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.tile.Tile;

/**
 * Tile component. Makes this entity a tile.
 * @author vanilla
 *
 */
public class CTile implements Poolable, Component {

	public Tile myTile;

	@Override
	public void reset() {
		myTile = null;
	}

}
