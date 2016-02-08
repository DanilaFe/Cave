package com.danilafe.cave.attacks;

/**
 * Class calculating weapon properties from the weapon parameters.
 * @author vanilla
 *
 */
public abstract class WeaponPropertiesCalculator {

	/**
	 * Calculates the properties from the given weapon
	 * @param weapon the weapon whose properties to calculate
	 * @return a new weapon parameter with adjusted variables.
	 */
	public abstract WeaponProperties calculateProperties(Weapon weapon);

}
