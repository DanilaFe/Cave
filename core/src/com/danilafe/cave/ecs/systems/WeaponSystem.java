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

public class WeaponSystem extends IteratingSystem {

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
		WeaponProperties generatedProperties = weapon.weapon.propertiesCalculator.calculateProperties(weapon.weapon);
		weaponBounds.bounds.setSize(generatedProperties.size.x, generatedProperties.size.y);
		weaponFollowing.offset.set(generatedProperties.offset);
		weaponDamageCause.damage = generatedProperties.damage;
		weaponDamageCause.maxDelay = generatedProperties.delay;
		weaponDamageCause.additionalKnockback.set(generatedProperties.additionalKnockback);
		weaponDamageCause.knockback = generatedProperties.knockback;

	}

}
