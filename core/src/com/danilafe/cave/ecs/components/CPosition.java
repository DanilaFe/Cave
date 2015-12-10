package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Position component. Holds the position of the entity
 * @author vanilla
 *
 */
public class CPosition implements Poolable, Component {

	/**
	 * Vector2 holding the x and y coordinates in the game world.
	 */
	public Vector2 position = new Vector2(0, 0);
	
	@Override
	public void reset() {
		position.set(0, 0);
	}	
	
}
