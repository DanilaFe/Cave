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

	public Vector2 previousSpeed = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
	public float maxSpeed = Float.MAX_VALUE;
	public float damagePerUnit = 0;

	@Override
	public void reset() {
		damagePerUnit = 0;
		maxSpeed = Float.MAX_VALUE;
		previousSpeed.set(Float.MAX_VALUE, Float.MAX_VALUE);
	}

}
