package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Speed component. This holds the speed vector of an entity.
 * @author vanilla
 *
 */
public class CSpeed implements Poolable, Component{

	/**
	 * The vector that holds the speed.
	 */
	public Vector2 speed = new Vector2(0, 0);
	
	@Override
	public void reset() {
		speed.set(0, 0);
	}
	
	
}
