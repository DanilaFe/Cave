package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CFrictionCause implements Poolable, Component {

	public Vector2 frictionMultiplier = new Vector2(1, 1);
	
	@Override
	public void reset() {
		frictionMultiplier.set(1, 1);
	}

}
