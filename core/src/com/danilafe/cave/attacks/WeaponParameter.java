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
	public float weaponDuration;
	/**
	 * The damage this weapon deals
	 */
	public float weaponDamage = 0;
	/**
	 * The knockback of this weapon
	 */
	public float weaponKnockback = 0;
	/**
	 * The additional knockback of this weapon
	 */
	public Vector2 additionalKnockback = new Vector2(0, 0);
	/**
	 * The size of the weapon
	 */
	public Vector2 weaponSize = new Vector2(0, 0);
	/**
	 * The delay of this weapon
	 */
	public float weaponDelay = 0;

}
