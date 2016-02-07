package com.danilafe.cave.attacks;

import com.badlogic.gdx.math.Vector2;

/**
 * Properties of a weapon entity that change as the attack goes on.
 * @author vanilla
 *
 */
public class WeaponProperties {

	/**
	 * The offset of this weapon from the player's collision box.
	 */
	public Vector2 offset = new Vector2(0, 0);
	/**
	 * The size (width, height) of the weapon's collision box.
	 */
	public Vector2 size = new Vector2(0, 0);
	/**
	 * The damage of this weapon
	 */
	public float damage = 0;
	/**
	 * The knockback of this weapon
	 */
	public float knockback = 0;
	/**
	 * The additional knockback of this weapon
	 */
	public Vector2 additionalKnockback = new Vector2(0, 0);
	/**
	 * The delay of this weapon
	 */
	public float delay = 0;

}
