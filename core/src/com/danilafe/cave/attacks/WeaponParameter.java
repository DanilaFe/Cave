package com.danilafe.cave.attacks;

import com.badlogic.gdx.math.Vector2;

/**
 * Description of a weapon
 * @author vanilla
 *
 */
public class WeaponParameter {

	/**
	 * The duration of this weapon's attack
	 */
	public float duration;
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
