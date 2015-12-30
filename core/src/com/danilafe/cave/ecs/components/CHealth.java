package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

public class CHealth implements Poolable, Component {

	public float health;
	public float maxHealth;
	public ECSRunnable onNoHealth;

	@Override
	public void reset() {
		health = 0;
		maxHealth = 0;
		onNoHealth = null;
	}

}
