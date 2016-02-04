package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;

/**
 * PositionSystem - adds the speed to the entity's position.
 * @author vanilla
 *
 */
public class PositionSystem extends IteratingSystem {

	/**
	 * Creates a new PositionSystem
	 */
	public PositionSystem() {
		super(Family.all(CPosition.class).all(CSpeed.class).exclude(CDisabled.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CSpeed entitySpeed = entity.getComponent(CSpeed.class);
		CPosition entityPosition = entity.getComponent(CPosition.class);

		if (entitySpeed != null)  entityPosition.position.add(entitySpeed.speed.cpy().scl(deltaTime));
	}

}
