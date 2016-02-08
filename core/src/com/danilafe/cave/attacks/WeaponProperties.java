package com.danilafe.cave.attacks;

import com.badlogic.gdx.math.Vector2;

/**
 * The current state of a weapon during the attack. Offset relative to enemy holding weapon.
 * @author vanilla
 *
 */
public class WeaponProperties extends WeaponData {

	/**
	 * The offset of this weapon.
	 */
	public Vector2 wOffset = new Vector2(0, 0);

}
