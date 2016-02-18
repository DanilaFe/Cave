package com.danilafe.cave.attacks;

import com.badlogic.ashley.core.Entity;

/**
 * Class calculating weapon properties from the weapon parameters.
 * @author vanilla
 *
 */
public abstract class WeaponPropertiesCalculator {

	/**
	 * Calculates the properties from the given weapon
	 * @param weapon the weapon whose properties to calculate
	 * @param sourceEntity the entity who's attacking
	 * @param weaponEntity the weapon that's being used to attack
	 * @return a new weapon parameter with adjusted variables.
	 */
	public abstract WeaponProperties calculateProperties(Weapon weapon, Entity sourceEntity, Entity weaponEntity);

}
