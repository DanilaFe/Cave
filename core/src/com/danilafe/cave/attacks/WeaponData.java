package com.danilafe.cave.attacks;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface for things that might need to contain information about a weapon properties,
 * either for storing a weapon's base stats or to return information about the weapon's current state.
 * @author vanilla
 *
 */
public class WeaponData {

	/**
	 * The damage this weapon deals
	 */
	public float wDamage = 0;
	/**
	 * The knockback of this weapon
	 */
	public float wKnockback = 0;
	/**
	 * The additional knockback of this weapon
	 */
	public Vector2 wAdditionalKnockback = new Vector2(0, 0);
	/**
	 * The size of the weapon
	 */
	public Vector2 wSize = new Vector2(0, 0);
	/**
	 * The delay of this weapon
	 */
	public float wDelay = 0;

}
