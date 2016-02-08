package com.danilafe.cave.attacks;

/**
 * Weapon descriptor used to spawn weapon entities.
 * @author vanilla
 *
 */
public class WeaponDecriptor {


	/**
	 * The properties of the weapon
	 */
	public WeaponParameter weaponParameter = null;
	/**
	 * Calculator to determine how the weapon is used
	 */
	public WeaponPropertiesCalculator weaponType = null;
	/**
	 * Entity descriptor of this weapon. To spawn the entity.
	 */
	public String entityDescriptor = "";

}
