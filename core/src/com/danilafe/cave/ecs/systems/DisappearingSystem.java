package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CDisappearing;

/**
 * System that handles the disappearance of disappearing objects.
 * @author vanilla
 *
 */
public class DisappearingSystem extends IteratingSystem {

	/**
	 * Creates a new DisappearingSystem
	 * @param family
	 */
	public DisappearingSystem() {
		super(Family.all(CDisappearing.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if((entity.getComponent(CDisappearing.class).remaingTime -= deltaTime) < 0) Utils.removeEntity(entity);
	}

}
