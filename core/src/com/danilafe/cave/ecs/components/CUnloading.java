package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Represents an entity that unloads when the chunk it's in unloads
 */
public class CUnloading implements Poolable, Component{

	@Override
	public void reset() {

	}

}
