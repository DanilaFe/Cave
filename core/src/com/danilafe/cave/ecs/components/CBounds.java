package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CBounds implements Poolable, Component {

	private Rectangle bounds = new Rectangle(0, 0, 0, 0);
	
	@Override
	public void reset() {
		
	}

}
