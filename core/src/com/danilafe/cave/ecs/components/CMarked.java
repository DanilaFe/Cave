package com.danilafe.cave.ecs.components;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Marked class. Used in order to mark entities as things.
 * Used in things like floaters that only accelerate when they're hit by other entities, to avoid accelerating from each other.
 * @author vanilla
 *
 */
public class CMarked implements Poolable, Component{

	/**
	 * The list of marks this component carries.
	 */
	public HashMap<String, Boolean> marks = new HashMap<String, Boolean>();

	@Override
	public void reset() {
		marks.clear();
	}

}
