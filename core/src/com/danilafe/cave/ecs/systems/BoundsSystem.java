package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CPosition;

public class BoundsSystem extends IteratingSystem {

	public BoundsSystem() {
		super(Family.all(CBounds.class, CPosition.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CBounds entityBounds = entity.getComponent(CBounds.class);
		CPosition entityPosition = entity.getComponent(CPosition.class);
		entityBounds.bounds.setCenter(entityPosition.position);
	}

}
