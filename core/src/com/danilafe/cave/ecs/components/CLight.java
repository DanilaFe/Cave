package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.lights.Light;

public class CLight implements Poolable, Component{

	public Light light = Light.create(0, 0, 0, 0, 0, 0);
	public Vector2 offet = new Vector2(0, 0);

	@Override
	public void reset() {
		light.set(0, 0, 0, 0, 0, 0);
		offet.set(0, 0);
	}

}
