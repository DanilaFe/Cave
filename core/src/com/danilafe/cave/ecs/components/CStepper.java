package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * CStepper - Runs the runnable code every step. That's it.
 * @author vanilla
 *
 */
public class CStepper implements Poolable, Component {

	/**
	 * The runnable that will be executed.
	 */
	public ECSRunnable runnable;

	@Override
	public void reset() {
		runnable = null;
	}

}
