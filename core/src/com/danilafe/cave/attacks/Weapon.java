package com.danilafe.cave.attacks;

/**
 * Weapon data
 * @author vanilla
 *
 */
public class Weapon {

	/**
	 * The parameter of the weapon
	 */
	public WeaponParameter parameter;
	/**
	 * The weapon properties calculator
	 */
	public ComboChain currentChain;
	/**
	 * The current delay of the weapon
	 */
	public float remainingDuration = 0;

	/**
	 * Creates a new weapon from this weapon descriptor
	 * @param weaponDescriptor the weapon descriptor to gnerate from
	 * @return the created weapon
	 */
	public static Weapon create(WeaponDecriptor weaponDescriptor){
		Weapon newWeapon = new Weapon();
		newWeapon.parameter = weaponDescriptor.weaponParameter;
		newWeapon.currentChain = weaponDescriptor.weaponAttacks;
		return newWeapon;
	}
}
