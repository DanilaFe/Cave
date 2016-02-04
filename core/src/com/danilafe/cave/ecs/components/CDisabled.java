package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CDisabled implements Poolable, Component {

	public String reason;

	@Override
	public void reset() {
		reason = "";
	}

}
