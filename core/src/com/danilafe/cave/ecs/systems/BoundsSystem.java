package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CPosition;

/**
 * BoundsSystem - Takes position and bounds, and sets the bounds' center to the position.
 * @author vanilla
 *
 */
public class BoundsSystem extends IteratingSystem {

	/**
	 * Creates a new BoundsSystem.
	 */
	public BoundsSystem() {
		super(Family.all(CBounds.class, CPosition.class).exclude(CDisabled.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CBounds entityBounds = entity.getComponent(CBounds.class);
		CPosition entityPosition = entity.getComponent(CPosition.class);
		entityBounds.bounds.setCenter(entityPosition.position);
	}

}
