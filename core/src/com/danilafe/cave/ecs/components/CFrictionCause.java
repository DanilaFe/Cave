package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * CFrictionCause - marks this entity as causing friction when its edges are being touched by the edges of another entity.
 * @author vanilla
 *
 */
public class CFrictionCause implements Poolable, Component {

	/**
	 * By how much the friction slows the other entity down.
	 */
	public Vector2 frictionMultiplier = new Vector2(1, 1);
	
	@Override
	public void reset() {
		frictionMultiplier.set(1, 1);
	}

}
