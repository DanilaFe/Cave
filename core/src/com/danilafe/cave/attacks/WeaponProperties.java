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

	public float damage = 0;

}
