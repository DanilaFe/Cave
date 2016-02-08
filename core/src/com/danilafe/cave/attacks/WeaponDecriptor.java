package com.danilafe.cave.attacks;

import com.danilafe.cave.creation.EntityDescriptor;

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
	public EntityDescriptor entityDescriptor = null;

	public static WeaponDecriptor create(WeaponParameter parameter, WeaponPropertiesCalculator calculator, EntityDescriptor entity){
		WeaponDecriptor weaponDescriptor = new WeaponDecriptor();
		weaponDescriptor.weaponParameter = parameter;
		weaponDescriptor.weaponType = calculator;
		weaponDescriptor.entityDescriptor = entity;
		return weaponDescriptor;
	}

}
