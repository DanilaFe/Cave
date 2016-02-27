package com.danilafe.cave.attacks;

import com.badlogic.gdx.math.Vector2;

/**
 * Description of a weapon's properties
 * @author vanilla
 *
 */
public class WeaponParameter extends WeaponData {

	/**
	 * Creates a new WeaponParameter ith the given data
	 * @param damage the damage caused by the weapon
	 * @param knockback the knockback from the weapon
	 * @param additionalKnockback the additional knockback vector
	 * @param size the size of the weapon
	 * @param delay delay during which weapon deals no damage
	 * @return the created weapon parameter
	 */
	public static WeaponParameter create(float damage, float knockback, Vector2 additionalKnockback, Vector2 size, float delay){
		WeaponParameter newParameter = new WeaponParameter();
		newParameter.wDamage = damage;
		newParameter.wKnockback = knockback;
		newParameter.wAdditionalKnockback.set(additionalKnockback);
		newParameter.wSize.set(size);
		newParameter.wDelay = delay;
		return newParameter;
	}

}
