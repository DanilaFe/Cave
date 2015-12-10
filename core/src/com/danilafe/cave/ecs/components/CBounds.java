package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Bounds component. Holds a rectangle that is updated with the position and the size of this entity.
 * @author vanilla
 *
 */
public class CBounds implements Poolable, Component {

	/**
	 * The rectangle that defines the bounds of this entity.
	 */
	public Rectangle bounds = new Rectangle(0, 0, 0, 0);
	
	@Override
	public void reset() {
		
	}

}
