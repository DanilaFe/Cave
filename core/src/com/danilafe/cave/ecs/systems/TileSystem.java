package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
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
			CAnchor anchor = entityB.getComponent(CAnchor.class);
			Vector2 oldPos = anchor.anchor.position.cpy();
			anchor.anchor.position.set(entityB.getComponent(CPosition.class).position);
			if(!(oldPos.x == anchor.anchor.position.x && oldPos.y == anchor.anchor.position.y))
				CaveGame.instance.mapManager.needsUpdate = true;
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
