package com.danilafe.cave.creation;

import com.badlogic.ashley.core.Entity;

/**
 * An entity descriptor. These are used in order to create similar entities.
 * @author vanilla
 *
 */
public abstract class EntityDescriptor {

	/**
	 * Creates an entity it describes.
	 * Entity is spawned at the given location if the entity has a position component.
	 * @param x the x position of the new entity
	 * @param y the y position of the new entity
	 * @return the created entity
	 */
	public abstract Entity create(float x, float y);
	
}
