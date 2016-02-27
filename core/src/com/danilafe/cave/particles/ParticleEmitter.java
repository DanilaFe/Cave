package com.danilafe.cave.particles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.creation.EntityDescriptor;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CDisappearing;
import com.danilafe.cave.ecs.components.CMarked;

/**
 * A particle emitter spawning particles.
 * @author vanilla
 *
 */
public class ParticleEmitter {

	/**
	 * The position of this emitter
	 */
	public Vector2 position = new Vector2();
	/**
	 * The offset of this emitter
	 */
	public Vector2 offset = new Vector2();
	/**
	 * The minimum delay (in seconds) before a new particle is spawned
	 */
	public float minDelay;
	/**
	 * How much longer than the minimum delay it's possible to wait
	 */
	public float maxDelayFluctuation;
	/**
	 * The minimum range of the emitter
	 */
	public float minRange;
	/**
	 * The maximum distance beyond the minimum range this emitter can go
	 */
	public float maxRangeFluctuation;
	/**
	 * The minimum direction, in degrees, in which particles are spawned from the emitter
	 */
	public float minDirection;
	/**
	 * The maximum additional angle beyond the minimum angle at which particles can be spawned
	 */
	public float maxDirectionFluctuation;
	/**
	 * Delay remaining before next particle is spawned.
	 */
	public float delay;
	/**
	 * The type of entity that's spawned as a particle
	 */
	public EntityDescriptor entityType;
	/**
	 * If true, and the spawned entity has a CMarked component, the emitter will add a "particle" marker to the component.
	 */
	public boolean markAsParticle;
	/**
	 * The minimum ammount of time this particle will live
	 */
	public float minParticleLife;
	/**
	 * The maximum amount over the minimum time this particle will live
	 */
	public float maxParticleLifeFluctuation;

	/**
	 * Updates this emitter.
	 * @param deltaTime the delta time between steps
	 */
	public void update(float deltaTime) {
		delay -= deltaTime;
		while(delay <= 0){
			if(entityType == null) return;
			float addedDelay = (float) (Math.random() * maxDelayFluctuation + minDelay);
			float direction = (float) (Math.random() * maxDirectionFluctuation + minDirection);
			float distance = (float) (Math.random() * maxRangeFluctuation + minRange);
			float life = (float) (Math.random() * maxParticleLifeFluctuation + minParticleLife);

			Vector2 offset = new Vector2(1, 0);
			offset.setAngle(direction);
			offset.setLength(distance);
			offset.add(position).add(this.offset);

			Entity newParticle = entityType.create(offset.x, offset.y);
			if(markAsParticle && Family.all(CMarked.class).exclude(CDisabled.class).get().matches(newParticle)) newParticle.getComponent(CMarked.class).marks.put("particle", true);
			CDisappearing disappearing = CaveGame.instance.pooledEngine.createComponent(CDisappearing.class);
			disappearing.remaingTime = life;
			newParticle.add(disappearing);
			CaveGame.instance.pooledEngine.addEntity(newParticle);
			delay += addedDelay;
		}
	}

	/**
	 * Sets this emitter to use the given parameters
	 * @param x the x-position of the emitter
	 * @param y the y-position of the emitter
	 * @param minDelay the minimum delay between emissions
	 * @param maxDelayF the maximum amount by which the minimum delay can increase
	 * @param minDistance the minimum distances from the emitter
	 * @param maxDistanceF the maximum amount by which the minimum distance can increase
	 * @param minDir the minimum direction of particles from the emitter
	 * @param maxDirF the maximum mount by which the minimum direction can increase
	 * @param minLife the minimum lifespan of entities
	 * @param maxLifeF the max fluctuation in the lifespan of entities
	 * @param entityType the entity parameter of this emitter
	 * @param markAsParticle whether to mark this entity as a particle
	 * @param offsetX the x-offset of the emitter
	 * @param offsetY the y-offset of the emitter
	 */
	public void set(float x, float y, float minDelay, float maxDelayF, float minDistance, float maxDistanceF, float minDir, float maxDirF, float minLife, float maxLifeF, EntityDescriptor entityType, boolean markAsParticle, float offsetX, float offsetY){
		position.set(x, y);
		this.minDelay = minDelay;
		maxDelayFluctuation = maxDelayF;
		minDirection = minDir;
		maxDirectionFluctuation = maxDirF;
		minRange = minDistance;
		maxRangeFluctuation = maxDistanceF;
		this.markAsParticle = markAsParticle;
		this.entityType = entityType;
		minParticleLife = minLife;
		maxParticleLifeFluctuation = maxLifeF;
		offset.set(offsetX, offsetY);
	}

}
