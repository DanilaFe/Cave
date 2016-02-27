package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * The SpeedDamage component deals damage to the entity if its speed changes too fast.
 * @author vanilla
 *
 */
public class CSpeedDamage implements Poolable, Component {

	/**
	 * Previous speed of this entity
	 */
	public Vector2 previousSpeed = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
	/**
	 * Maximum speed change before damage is dealt
	 */
	public float maxSpeed = Float.MAX_VALUE;
	/**
	 * How much damage is dealt for exceeding the speed limit
	 */
	public float damagePerUnit = 0;

	@Override
	public void reset() {
		damagePerUnit = 0;
		maxSpeed = Float.MAX_VALUE;
		previousSpeed.set(Float.MAX_VALUE, Float.MAX_VALUE);
	}

}
