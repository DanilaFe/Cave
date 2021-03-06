package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * CFrictionObject - used to flag an entity as being susceptible to friction
 * @author vanilla
 *
 */
public class CFrictionObject implements Poolable, Component {

	/**
	 * By how much the friction should slow the entity down. This is multiplied by the speed to get the new speed.
	 */
	public Vector2 frictionCoefficient = new Vector2(1, 1);

	@Override
	public void reset() {
		frictionCoefficient.set(1, 1);
	}

}
