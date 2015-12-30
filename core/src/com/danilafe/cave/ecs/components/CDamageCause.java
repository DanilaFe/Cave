package com.danilafe.cave.ecs.components;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

public class CDamageCause implements Poolable, Component {

	public float damage = 0;

	public float delay;
	public float maxDelay;

	public ECSRunnable onDamage;

	public ArrayList<String> teams = new ArrayList<String>();

	@Override
	public void reset() {
		damage = 0;
		teams.clear();
		onDamage = null;
		delay = 0;
		maxDelay = 0;
	}

}
