package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.tile.ChunkAnchor;

public class CAnchor implements Poolable, Component{

	public ChunkAnchor anchor;

	@Override
	public void reset() {
		anchor = null;
	}

}
