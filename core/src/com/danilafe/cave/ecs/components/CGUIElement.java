package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
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
	 * Whether this entity's GUI element should be rendered as a top-level element.
	 */
	public boolean topLevel = false;

	@Override
	public void reset() {
		guiElement = null;
		topLevel = false;
	}

}
