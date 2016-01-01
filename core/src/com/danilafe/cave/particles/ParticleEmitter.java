package com.danilafe.cave.particles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.creation.EntityDescriptor;
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
	public String entityType;
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
	 * The descriptor of the entity type. Used for caching to prevent requesting the entity descriptor multiple times.
	 */
	private EntityDescriptor retreivedDescriptor;

	/**
	 * Updates this emitter.
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		delay -= deltaTime;
		while(delay <= 0){
			if(retreivedDescriptor == null) retreivedDescriptor = CaveGame.instance.creationManager.entityDescriptors.get(entityType);
			float addedDelay = (float) (Math.random() * maxDelayFluctuation + minDelay);
			float direction = (float) (Math.random() * maxDirectionFluctuation + minDirection);
			float distance = (float) (Math.random() * maxRangeFluctuation + minRange);
			float life = (float) (Math.random() * maxParticleLifeFluctuation + minParticleLife);

			Vector2 offset = new Vector2(1, 0);
			offset.setAngle(direction);
			offset.setLength(distance);
			offset.add(position).add(this.offset);

			Entity newParticle = retreivedDescriptor.create(offset.x, offset.y);
			if(markAsParticle && Family.all(CMarked.class).get().matches(newParticle)) newParticle.getComponent(CMarked.class).marks.put("particle", true);
			CDisappearing disappearing = CaveGame.instance.pooledEngine.createComponent(CDisappearing.class);
			disappearing.remaingTime = life;
			newParticle.add(disappearing);
			CaveGame.instance.pooledEngine.addEntity(newParticle);
			delay += addedDelay;
		}
	}

	public void set(float x, float y, float minDelay, float maxDelayF, float minDistance, float maxDistanceF, float minDir, float maxDirF, float minLife, float maxLifeF, String entityType, boolean markAsParticle, float offsetX, float offsetY){
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
