package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

/**
 * FamilySystem - A parent class meant to be overridden.
 * This entity keeps track of two families of entities, allowing for efficient operation on two 
 * related sets of entities 
 * @author vanilla
 *
 */
public class FamilySystem extends EntitySystem {

	/**
	 * Listener used to update list of entities in family A
	 */
	public EntityListener listenerA;
	/**
	 * Listener used to update list of entities in family B
	 */
	public EntityListener listenerB;
	/**
	 * First family to keep track of.
	 */
	public Family familyA;
	/**
	 * Second family to keep track of.
	 */
	public Family familyB;
	/**
	 * Updated list of entities in FamilyA
	 */
	public ImmutableArray<Entity> entitiesA;
	/**
	 * Updated list of entities in FamilyB
	 */
	public ImmutableArray<Entity> entitiesB;
	/**
	 * The engine this entity is attached to.
	 */
	public Engine listenFor;
	
	/**
	 * Creates a new FamilySystem operating on families A and B
	 * @param a family A to keep track of
	 * @param b family B to keep track of
	 */
	public FamilySystem(Family a, Family b){
		familyA = a;
		familyB = b;
		listenerA = new EntityListener() {		
			@Override
			public void entityRemoved(Entity entity) {
				entitiesA = listenFor.getEntitiesFor(familyA);
			}
			
			@Override
			public void entityAdded(Entity entity) {
				entitiesA = listenFor.getEntitiesFor(familyA);
			}
		};
		listenerB = new EntityListener() {		
			@Override
			public void entityRemoved(Entity entity) {
				entitiesB = listenFor.getEntitiesFor(familyB);
			}
			
			@Override
			public void entityAdded(Entity entity) {
				entitiesB = listenFor.getEntitiesFor(familyB);
			}
		};
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		listenFor = engine;
		engine.addEntityListener(familyA, listenerA);
		engine.addEntityListener(familyB, listenerB);
		entitiesA = listenFor.getEntitiesFor(familyA);
		entitiesB = listenFor.getEntitiesFor(familyB);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		listenFor = null;
		engine.removeEntityListener(listenerA);
		engine.removeEntityListener(listenerB);
		entitiesA = null;
		entitiesB = null;
	}
	
}
