package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CGUIElement;
import com.danilafe.cave.ecs.components.CPosition;

/**
 * System that updates element positions from their entities' positions
 * @author vanilla
 *
 */
public class GUISystem extends FamilySystem {

	/**
	 * Creates a new GUI system
	 */
	public GUISystem() {
		super(Family.all(CGUIElement.class).exclude(CDisabled.class).get(),
				Family.all(CGUIElement.class, CPosition.class).exclude(CDisabled.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for(Entity entity : entitiesB)
			entity.getComponent(CGUIElement.class).guiElement.worldPos.set(entity.getComponent(CPosition.class).position);
	}

	@Override
	public void entityBAdded(Entity e) {
		super.entityBAdded(e);
		CaveGame.instance.renderSystem.renderWindows.add(e.getComponent(CGUIElement.class).guiElement);
	}

	@Override
	public void entityBRemoved(Entity e) {
		super.entityBRemoved(e);
		CaveGame.instance.renderSystem.renderWindows.remove(e.getComponent(CGUIElement.class).guiElement);
	}

}
