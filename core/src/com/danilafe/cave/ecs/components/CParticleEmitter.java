package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.particles.ParticleEmitter;

/**
 * A particle emitter component.
 * @author vanilla
 *
 */
public class CParticleEmitter implements Poolable, Component{

	/**
	 * Emitter of this entity
	 */
	public ParticleEmitter emitter = new ParticleEmitter();

	@Override
	public void reset() {
		emitter.entityType = "";
	}

}
