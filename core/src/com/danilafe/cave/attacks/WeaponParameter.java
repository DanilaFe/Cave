package com.danilafe.cave.attacks;

import com.badlogic.gdx.math.Vector2;

/**
 * Description of a weapon's properties
 * @author vanilla
 *
 */
public class WeaponParameter extends WeaponData {

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
