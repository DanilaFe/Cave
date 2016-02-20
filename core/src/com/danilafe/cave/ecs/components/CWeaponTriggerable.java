package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Component that makes the entity's weapons triggered by keystrokes.
 * @author vanilla
 *
 */
public class CWeaponTriggerable implements Poolable, Component {

	/**
	 * The attack key code
	 */
	public int attackKeycode = 0;

	@Override
	public void reset() {
		attackKeycode = 0;
	}

}
