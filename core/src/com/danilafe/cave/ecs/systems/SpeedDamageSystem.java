package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CDamageable;
import com.danilafe.cave.ecs.components.CHealth;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.components.CSpeedDamage;

/**
 * SpeedDamageSystem deals damage to entities whose speeds change suddenly.
 * @author vanilla
 *
 */
public class SpeedDamageSystem extends IteratingSystem {

	/**
	 * Creates a new SpeedDamageSystem
	 */
	public SpeedDamageSystem() {
		super(Family.all(CSpeedDamage.class, CSpeed.class, CHealth.class, CDamageable.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CSpeed speed = entity.getComponent(CSpeed.class);
		CSpeedDamage speedDamage = entity.getComponent(CSpeedDamage.class);

		if(!(speedDamage.previousSpeed.x == speedDamage.previousSpeed.y  && speedDamage.previousSpeed.x == Float.MAX_VALUE)){
			float speedDifference = speed.speed.len() - speedDamage.previousSpeed.len();
			System.out.println(speedDifference);
			float damage = Math.max((Math.abs(speedDifference) - speedDamage.maxSpeed), 0);
			if(damage > 0)
				Utils.dealDamage(entity, damage, true, true, false, deltaTime);
		}

		speedDamage.previousSpeed.set(speed.speed);
	}

}
