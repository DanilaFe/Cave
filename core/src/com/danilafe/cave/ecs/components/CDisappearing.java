package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * A component that marks this entity as scheduled to disappear when the remainingTime runs out.
 * @author vanilla
 *
 */
public class CDisappearing implements Poolable, Component {

	/**
	 * Time remaining before the disappearance
	 */
	public float remaingTime = 0;
	/**
	 * Executed when this entity disappears
	 */
	public ECSRunnable onRemove = null;

	@Override
	public void reset() {
		remaingTime = 0;
		onRemove = null;
	}

}
