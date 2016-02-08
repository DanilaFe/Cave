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
	public WeaponPropertiesCalculator propertiesCalculator;
	/**
	 * The current delay of the weapon
	 */
	public float remainingDuration = 0;

	public static Weapon create(WeaponDecriptor weaponDescriptor){
		Weapon newWeapon = new Weapon();
		newWeapon.parameter = weaponDescriptor.weaponParameter;
		newWeapon.propertiesCalculator = weaponDescriptor.weaponType;
		return newWeapon;
	}
}
