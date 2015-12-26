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
	/**
	 * The maximum distance the player has to be from the camera before it moves after him on the x-axis
	 */
	public float maxOffsetX = 0;
	/**
	 * The maximum distance the player has to be from the camera before it moves after him on the y-axis
	 */
	public float maxOffsetY = 0;

	@Override
	public void reset() {
		camera = null;
	}

}
