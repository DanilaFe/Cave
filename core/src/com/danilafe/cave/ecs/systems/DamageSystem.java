package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CDamageCause;
import com.danilafe.cave.ecs.components.CDamageable;
import com.danilafe.cave.ecs.components.CHealth;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.health.DamageData;

public class DamageSystem extends FamilySystem {

	public DamageSystem() {
		super(Family.all(CDamageable.class, CBounds.class, CHealth.class).get(), Family.all(CDamageCause.class, CBounds.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for(int i = 0; i < entitiesB.size(); i++){
			Entity damageCauseEntity = entitiesB.get(i);
			CDamageCause damageCause = damageCauseEntity.getComponent(CDamageCause.class);

			damageCause.delay -= deltaTime;
			if(damageCause.delay < 0) damageCause.delay = 0;
		}
		for(int i = 0; i < entitiesA.size(); i++){
			Entity damageableentity = entitiesA.get(i);
			CDamageable damageeable = damageableentity.getComponent(CDamageable.class);

			damageeable.delay -= deltaTime;
			if(damageeable.delay < 0) damageeable.delay = 0;
		}
		for(int i = 0; i < entitiesA.size(); i++){
			Entity damageable = entitiesA.get(i);
			CDamageable damageableComponent = damageable.getComponent(CDamageable.class);
			CBounds damageableBounds = damageable.getComponent(CBounds.class);
			CHealth damageableHealth = damageable.getComponent(CHealth.class);
			CSpeed speed = damageable.getComponent(CSpeed.class);

			for(int j = 0; j < entitiesB.size(); j++){
				if(damageableComponent.delay > 0) continue;

				Entity damageCause = entitiesB.get(j);
				CDamageCause damageCauseComponent = damageCause.getComponent(CDamageCause.class);
				if(damageCauseComponent.delay > 0) continue;

				CBounds damageCauseBounds = damageCause.getComponent(CBounds.class);
				if(damageCauseComponent.teams.contains(damageableComponent.team) && damageCauseBounds.bounds.overlaps(damageableBounds.bounds)) {
					DamageData damageData = new DamageData();
					damageData.damageFrom = damageCause;
					damageData.damageTo = damageable;
					damageData.damage = damageableComponent.damageMutliplier * damageCauseComponent.damage;

					damageableComponent.currentDamage = damageData;
					if(damageableComponent.onDamage != null) damageableComponent.onDamage.update(damageable, deltaTime);
					if(damageableHealth != null) damageableHealth.health -= damageData.damage;
					damageableComponent.currentDamage = null;
					if(damageableHealth != null && damageableHealth.health <= 0 && damageableHealth.onNoHealth != null) damageableHealth.onNoHealth.update(damageable, deltaTime);
					damageableComponent.delay = damageableComponent.maxDelay;
					damageCauseComponent.delay = damageCauseComponent.maxDelay;
					if(damageCauseComponent.onDamage != null) damageCauseComponent.onDamage.update(damageCause, deltaTime);


					if(speed != null){
						Vector2 damageableCenter = new Vector2();
						damageableBounds.bounds.getCenter(damageableCenter);
						Vector2 damageCenter = new Vector2();
						damageCauseBounds.bounds.getCenter(damageCenter);

						float angle = damageableCenter.sub(damageCenter).angle(); // We won't be needing it afterwards, so don't copy
						damageCenter.set(1, 1).setLength(damageCauseComponent.knockback * damageableComponent.knockbackMultiplier).setAngle(angle); // Reuse damage center
						speed.speed.add(damageCenter).add(damageCauseComponent.additionalKnockback.cpy().scl(damageableComponent.knockbackMultiplier));
					}
				}
			}
		}
	}


}
