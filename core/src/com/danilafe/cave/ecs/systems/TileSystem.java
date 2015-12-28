package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.ecs.components.CAnchor;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CTile;

/**
 * TileSystem - updates chunk anchors and tiles.
 * @author vanilla
 *
 */
public class TileSystem extends FamilySystem {

	/**
	 * Creates a new TileSystem
	 */
	public TileSystem() {
		super(Family.all(CTile.class).get(), Family.all(CAnchor.class, CPosition.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		for(Entity entityB : entitiesB) {
			entityB.getComponent(CAnchor.class).anchor.position.set(entityB.getComponent(CPosition.class).position);
		}
	}

	@Override
	public void entityBAdded(Entity e) {
		CaveGame.instance.mapManager.anchors.add(e.getComponent(CAnchor.class).anchor);
	}

	@Override
	public void entityBRemoved(Entity e) {
		CaveGame.instance.mapManager.anchors.remove(e.getComponent(CAnchor.class).anchor);
	}

}
