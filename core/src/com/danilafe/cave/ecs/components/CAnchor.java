package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.tile.ChunkAnchor;

/**
 * Anchor component used to represent and update entities that load chunks around them
 * @author vanilla
 *
 */
public class CAnchor implements Poolable, Component{

	/**
	 * The anchor this entity represents
	 */
	public ChunkAnchor anchor;

	@Override
	public void reset() {
		anchor = null;
	}

}
