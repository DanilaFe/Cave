package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CParticleEmitter;
import com.danilafe.cave.ecs.components.CPosition;

/**
 * A system that updates particle emitters
 * @author vanilla
 *
 */
public class EmitterSystem extends IteratingSystem{

	/**
	 * Creates a new EmitterSystem
	 */
	public EmitterSystem() {
		super(Family.all(CParticleEmitter.class, CPosition.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CPosition pos = entity.getComponent(CPosition.class);
		CParticleEmitter emi = entity.getComponent(CParticleEmitter.class);
		emi.emitter.position.set(pos.position);
		emi.emitter.update(deltaTime);
	}

}
