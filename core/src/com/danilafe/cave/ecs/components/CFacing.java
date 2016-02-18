package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Describes an entity facing in a particular direction.
 * @author vanilla
 *
 */
public class CFacing implements Poolable, Component {

	/**
	 * The direction it's facing
	 */
	public Direction facing = Direction.LEFT;

	@Override
	public void reset() {
		facing = Direction.LEFT;
	}

	/**
	 * Enum for direction entities.
	 * @author vanilla
	 *
	 */
	public static enum Direction {
		LEFT, RIGHT
	}

}
