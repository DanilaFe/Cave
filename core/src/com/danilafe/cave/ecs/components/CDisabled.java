package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Represents a component that would not be processed by the system
 * @author vanilla
 *
 */
public class CDisabled implements Poolable, Component {

	/**
	 * Why this component is disabled
	 */
	public String reason;

	@Override
	public void reset() {
		reason = "";
	}

}
