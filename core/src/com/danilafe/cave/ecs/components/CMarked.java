package com.danilafe.cave.ecs.components;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CMarked implements Poolable, Component{

	public HashMap<String, Boolean> marks = new HashMap<String, Boolean>();

	@Override
	public void reset() {
		marks.clear();
	}

}
