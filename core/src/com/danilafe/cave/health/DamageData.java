package com.danilafe.cave.health;

import com.badlogic.ashley.core.Entity;

/**
 * DamageData used by damaged entities to keep track of important damage information
 * @author vanilla
 *
 */
public class DamageData {

	/**
	 * The damaged entity
	 */
	public Entity damageTo;
	/**
	 * The damaging entity
	 */
	public Entity damageFrom;
	/**
	 * The damage dealt
	 */
	public float damage;

}
