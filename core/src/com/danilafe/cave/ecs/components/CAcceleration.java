package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Acceleration component. Accelerates the entity.
 * @author vanilla
 *
 */
public class CAcceleration implements Poolable, Component{

	/**
	 * Acceleration value will be ADDED to the entity's speed.
	 */
	public Vector2 linearAcceleration = new Vector2(0, 0);
	/**
	 * Scalar values will be MULTIPLIED to the entity's speed.
	 */
	public Vector2 scalarAcceleration = new Vector2(1, 1);

	@Override
	public void reset() {
		linearAcceleration.set(0, 0);
		scalarAcceleration.set(1, 1);
	}

}
