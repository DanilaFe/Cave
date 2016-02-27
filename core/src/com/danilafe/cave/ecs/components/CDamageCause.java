package com.danilafe.cave.ecs.components;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * A component describing an entity that causes damage
 * @author vanilla
 *
 */
public class CDamageCause implements Poolable, Component {

	/**
	 * The damage this entity causes
	 */
	public float damage = 0;
	/**
	 * The knockback this entity causes
	 */
	public float knockback = 0;
	/**
	 * Additional knockback from this entity
	 */
	public Vector2 additionalKnockback = new Vector2(0, 0);

	/**
	 * Delay before this entity is able to hit again
	 */
	public float delay;
	/**
	 * The maximum delay before this entity is able to hit again
	 */
	public float maxDelay;

	/**
	 * ECSRunnable executed when this entity damages something
	 */
	public ECSRunnable onDamage;

	/**
	 * A list of teams this entity hits
	 */
	public ArrayList<String> teams = new ArrayList<String>();

	@Override
	public void reset() {
		damage = 0;
		teams.clear();
		onDamage = null;
		delay = 0;
		maxDelay = 0;
		knockback = 0;
		additionalKnockback.set(0, 0);
	}

}
