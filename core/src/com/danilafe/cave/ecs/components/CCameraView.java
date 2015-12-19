package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CCameraView implements Poolable, Component {

	public Camera camera = null;

	@Override
	public void reset() {
		camera = null;
	}

}
