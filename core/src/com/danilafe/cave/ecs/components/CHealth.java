package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * Health component representing and entity with health
 * @author vanilla
 *
 */
public class CHealth implements Poolable, Component {

	/**
	 * The ammount of health this entity has
	 */
	public float health;
	/**
	 * The maximum amounf of health this entity can have
	 */
	public float maxHealth;
	/**
	 * Executed when health falls below 0
	 */
	public ECSRunnable onNoHealth;

	@Override
	public void reset() {
		health = 0;
		maxHealth = 0;
		onNoHealth = null;
	}

}
