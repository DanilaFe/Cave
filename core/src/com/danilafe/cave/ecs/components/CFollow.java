package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Following component. This component follows anything that it's attached to.
 * @author vanilla
 *
 */
public class CFollow implements Poolable, Component {

	/**
	 * The entity to follow
	 */
	public Entity following;
	/**
	 * The offset at which to follow
	 */
	public Vector2 offset = new Vector2(0, 0);

	@Override
	public void reset() {
		following = null;
		offset.set(0, 0);
	}

}
