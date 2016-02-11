package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.attacks.WeaponProperties;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CDamageCause;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CFollow;
import com.danilafe.cave.ecs.components.CWeapon;

/**
 * Updates weapon entities based on their parameters
 * @author vanilla
 *
 */
public class WeaponSystem extends IteratingSystem {

	/**
	 * Creates a new WeaponSystem
	 */
	public WeaponSystem() {
		super(Family.all(CWeapon.class, CFollow.class, CBounds.class, CDamageCause.class).exclude(CDisabled.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CWeapon weapon = entity.getComponent(CWeapon.class);
		CFollow weaponFollowing = entity.getComponent(CFollow.class);
		CDamageCause weaponDamageCause = entity.getComponent(CDamageCause.class);
		CBounds weaponBounds = entity.getComponent(CBounds.class);

		weapon.weapon.remainingDuration += deltaTime;
		WeaponProperties generatedProperties = weapon.weapon.currentChain.attackType.calculateProperties(weapon.weapon);
		weaponBounds.bounds.setSize(generatedProperties.wSize.x, generatedProperties.wSize.y);
		weaponFollowing.offset.set(generatedProperties.wOffset);
		weaponDamageCause.damage = generatedProperties.wDamage;
		weaponDamageCause.maxDelay = generatedProperties.wDelay;
		weaponDamageCause.additionalKnockback.set(generatedProperties.wAdditionalKnockback);
		weaponDamageCause.knockback = generatedProperties.wKnockback;

	}

}
