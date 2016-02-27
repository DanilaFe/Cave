package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.health.DamageData;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * A component representing a damageable entity
 * @author vanilla
 *
 */
public class CDamageable implements Poolable, Component {

	/**
	 * By how much the knowback dealt by the damagecause entity is multiplied for this entity
	 */
	public float knockbackMultiplier = 1;
	/**
	 * By how much the damage scales when this entity is hit
	 */
	public float damageMutliplier = 1;
	/**
	 * If this entity is being processed right now, this is used to access who damaged it
	 */
	public DamageData currentDamage;
	/**
	 * Runnable that is executed on damage
	 */
	public ECSRunnable onDamage;

	/**
	 * Delay before this entity can take damage again
	 */
	public float delay;
	/**
	 * The maximum delay this entity can have
	 */
	public float maxDelay;

	/**
	 * The team this entity is on (used to check for damage)
	 */
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
