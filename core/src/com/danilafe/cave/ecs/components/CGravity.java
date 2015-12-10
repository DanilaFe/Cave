package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Gravity component. The gravity value will be added to the speed every second.
 * @author vanilla
 *
 */
public class CGravity implements Poolable, Component {

	/**
	 * The gravity value to add.
	 */
	public Vector2 gravity = new Vector2(0, 0);
	
	@Override
	public void reset() {
		gravity.set(0, 0);
	}
	
	
}
