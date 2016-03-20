package com.danilafe.cave.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstract class representing a task that is used to render to a sprite batch
 * @author vanilla
 *
 */
public abstract class RenderRunnable {

	/**
	 * Renders the object to the given sprite batch
	 * @param renderTo the sprite batch to render to
	 * @param me the object being rendered
	 */
	public abstract void render(SpriteBatch renderTo, Object me);

}
