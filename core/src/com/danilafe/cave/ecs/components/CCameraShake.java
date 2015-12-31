package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Screenshake component that modifies a camera offset
 * @author vanilla
 *
 */
public class CCameraShake implements Poolable, Component {

	/**
	 * The current camera offset
	 */
	public Vector2 currentOffset = new Vector2(0, 0);
	/**
	 * The camera offset it's moving towards
	 */
	public Vector2 nextOffset = new Vector2(0, 0);
	/**
	 * The distance from the center of the entity
	 */
	public float distance = 0;
	/**
	 * Multiplied by the distance each time the offset changes. Can be used to slow down the shake.
	 */
	public float distanceDamping = 1;
	/**
	 * The delay before the next offset is reached
	 */
	public float delay;
	/**
	 * The max delay between components
	 */
	public float maxDelay;
	/**
	 * If the distance value is below this, it gets set to 0.
	 */
	public float resetThreshold;

	@Override
	public void reset() {
		currentOffset.set(0, 0);
		nextOffset.set(0, 0);
		distance = 0;
		delay = 0;
		maxDelay = 0;
		distanceDamping = 1;
		resetThreshold = 0;
	}

}
