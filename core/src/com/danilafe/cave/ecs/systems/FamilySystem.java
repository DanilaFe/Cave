package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class FamilySystem extends EntitySystem {

	public EntityListener listenerA;
	public EntityListener listenerB;
	public Family familyA;
	public Family familyB;
	public ImmutableArray<Entity> entitiesA;
	public ImmutableArray<Entity> entitiesB;
	public Engine listenFor;
	
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
