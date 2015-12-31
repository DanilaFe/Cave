package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.health.DamageData;
import com.danilafe.cave.runnable.ECSRunnable;

public class CDamageable implements Poolable, Component {

	public float knockbackMultiplier = 1;
	public float damageMutliplier = 1;
	public DamageData currentDamage;
	public ECSRunnable onDamage;

	public float delay;
	public float maxDelay;

	public String team;

	@Override
	public void reset() {
		knockbackMultiplier = 1;
		damageMutliplier = 1;
		currentDamage = null;
		onDamage = null;
		delay = 0;
		maxDelay = 0;
		team = "";
	}

}
