package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
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
	/**
	 * The offset from the center of the player of this camera
	 */
	public Vector2 offset = new Vector2(0, 0);

	@Override
	public void reset() {
		camera = null;
		offset.set(0, 0);
		maxOffsetX = 0;
		maxOffsetY = 0;
	}

}
