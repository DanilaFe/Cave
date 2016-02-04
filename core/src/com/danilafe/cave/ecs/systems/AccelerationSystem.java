package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CAcceleration;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CSpeed;

/**
 * AccelerationSystem - used to apply acceleration to entities.
 * @author vanilla
 *
 */
public class AccelerationSystem extends IteratingSystem {

	/**
	 * Creates a new acceleration system.
	 */
	public AccelerationSystem() {
		super(Family.all(CSpeed.class, CAcceleration.class).exclude(CDisabled.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CSpeed speed = entity.getComponent(CSpeed.class);
		CAcceleration accel = entity.getComponent(CAcceleration.class);
		speed.speed.add(accel.linearAcceleration.cpy().scl(deltaTime));
		speed.speed.scl((float) Math.pow(accel.scalarAcceleration.x, deltaTime), (float) Math.pow(accel.scalarAcceleration.y, deltaTime));
	}

}
