package com.danilafe.cave.runnable;

import com.badlogic.ashley.core.Entity;

/**
 * An abstract class used to execute custom code for a component.
 * @author vanilla
 *
 */
public abstract class ECSRunnable {

	/**
	 * The abstract code to execute;
	 * @param me back reference to the entity
	 * @param myEngine the current engine
	 * @param deltaTime the delta time of the step
	 */
	public abstract void update(Entity me, float deltaTime);

}
