package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.gui.GUIElement;

/**
 * Represents an entity that is attached to a GUI element.
 * @author vanilla
 *
 */
public class CGUIElement implements Poolable, Component {

	/**
	 * The GUIElement associated with this entity
	 */
	public GUIElement guiElement = null;
	/**
	 * The offset of this GUI relative to the entity.
	 */
	public Vector2 offset = new Vector2(0, 0);
	/**
	 * Whether this entity's GUI element should be rendered as a top-level element.
	 */
	public boolean topLevel = false;

	@Override
	public void reset() {
		guiElement = null;
		topLevel = false;
		offset.set(0, 0);
	}

}
