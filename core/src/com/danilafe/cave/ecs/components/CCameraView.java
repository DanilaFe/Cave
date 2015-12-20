package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Camera view object. Makes the camera follow the entity.
 * @author vanilla
 *
 */
public class CCameraView implements Poolable, Component {

	/**
	 * The camera that should follow this entity.
	 */
	public Camera camera = null;

	@Override
	public void reset() {
		camera = null;
	}

}
