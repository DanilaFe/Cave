package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CInteractionCause;
import com.danilafe.cave.ecs.components.CInteractive;

/**
 * Interaction system that allows interactive entities to be, well, interacted with.
 * @author vanilla
 *
 */
public class InteractionSystem extends FamilySystem {

	/**
	 * Creates a new InteractionSystem
	 */
	public InteractionSystem() {
		super(Family.all(CInteractive.class, CBounds.class).get(), Family.all(CInteractionCause.class, CBounds.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for(int i = 0; i < entitiesA.size(); i++){
			Entity interactiveEntity = entitiesA.get(i);
			CBounds interactiveBounds = interactiveEntity.getComponent(CBounds.class);
			CInteractive interactive = interactiveEntity.getComponent(CInteractive.class);

			interactive.interactDelay -= deltaTime;
			if(interactive.interactDelay < 0) interactive.interactDelay = 0;

			if(interactive.interactKey != -1 && !Gdx.input.isKeyPressed(interactive.interactKey)) continue;
			if(interactive.onInteract == null) continue;

			for(int j = 0; j < entitiesB.size(); j++){
				if(interactive.interactDelay > 0) continue;

				Entity causeEntity = entitiesB.get(j);
				CBounds causeEntityBounds = causeEntity.getComponent(CBounds.class);
				if(!causeEntity.getComponent(CInteractionCause.class).canInteract) continue;
				if(!(causeEntityBounds.bounds.overlaps(interactiveBounds.bounds) || Utils.checkEdgeContact(interactiveBounds.bounds, causeEntityBounds.bounds))) continue;

				interactive.currentInteraction = causeEntity;
				interactive.onInteract.update(interactiveEntity, deltaTime);
				interactive.lastInteraction = causeEntity;
				interactive.currentInteraction = null;
				interactive.interactDelay = interactive.maxInteractDelay;
			}
		}
	}

}
