package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * InteractionCause component - means this entity can actively begin interaction with other entities.
 * @author vanilla
 *
 */
public class CInteractionCause implements Poolable, Component {

	/**
	 * Whether this entity can currently interact with something.
	 */
	public boolean canInteract = false;

	@Override
	public void reset() {
		canInteract = false;
	}

}
