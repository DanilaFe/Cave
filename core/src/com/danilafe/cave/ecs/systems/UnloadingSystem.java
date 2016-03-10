package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CUnloading;

/**
 * System to unload entities if they travel out of range
 * @author vanilla
 *
 */
public class UnloadingSystem extends IteratingSystem {

	/**
	 * Creates a new UnloadngSystem
	 */
	public UnloadingSystem() {
		super(Family.all(CUnloading.class, CPosition.class).exclude(CDisabled.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CPosition pos = entity.getComponent(CPosition.class);
		if(!CaveGame.instance.mapManager.getChunkAt(pos.position.x, pos.position.y).isLoaded)
			Utils.chunkEntity(entity);
	}
}
