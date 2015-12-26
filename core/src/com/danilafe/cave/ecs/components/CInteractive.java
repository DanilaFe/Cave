package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * Interactive component, making this entity able to interact with other entities that have the InteractionCause marker.
 * @author vanilla
 *
 */
public class CInteractive implements Poolable, Component {

	/**
	 * Determines what happens when this entity is interacted with.
	 */
	public ECSRunnable onInteract;

	/**
	 * The previous entity this entity interacted with
	 */
	public Entity lastInteraction;

	/**
	 * The current interaction this entity interacted with
	 */
	public Entity currentInteraction;
	/**
	 * The key that has to be held for the interaction to occur.
	 * If set to -1, interaction will always occur when this entity is touched.
	 */
	public int interactKey = -1;
	/**
	 * The time remaining before this entity can be interacted with again
	 */
	public float interactDelay = 0;
	/**
	 * How long it takes for this entity to be interactive again
	 */
	public float maxInteractDelay = 0;

	@Override
	public void reset() {
		onInteract = null;
		lastInteraction = null;
		currentInteraction = null;
		interactKey = -1;
		interactDelay = 0;
		maxInteractDelay = 0;
	}

}
