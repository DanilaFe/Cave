package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Constants;
import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.ecs.components.CAnchor;
import com.danilafe.cave.ecs.components.CAnimation;
import com.danilafe.cave.ecs.components.CDisabled;
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
		super(Family.all(CTile.class).exclude(CDisabled.class).get(), Family.all(CAnchor.class, CPosition.class).exclude(CDisabled.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		for(Entity entityA : entitiesA) {
			CTile tile = entityA.getComponent(CTile.class);
			if(tile.myTile.animationChange && tile.myTile.currentAnimation != null) {
				CAnimation animation = entityA.getComponent(CAnimation.class);
				animation.animationQueue.animationQueue.clear();
				Animation newAnimation = new Animation();
				newAnimation.animationParameter = tile.myTile.currentAnimation;
				animation.animationQueue.add(newAnimation);
				animation.rotation = tile.myTile.rotation;
				tile.myTile.animationChange = false;
			}
		}

		for(Entity entityB : entitiesB) {
			CAnchor anchor = entityB.getComponent(CAnchor.class);
			Vector2 oldPos = anchor.anchor.position.cpy();
			anchor.anchor.position.set(entityB.getComponent(CPosition.class).position);
			if(!(oldPos.x % Constants.CHUNK_SIZE == anchor.anchor.position.x % Constants.CHUNK_SIZE && oldPos.y % Constants.CHUNK_SIZE == anchor.anchor.position.y % Constants.CHUNK_SIZE))
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

	@Override
	public void entityAAdded(Entity e) {
		e.getComponent(CTile.class).myTile.animationChange = true;
	}

	@Override
	public void entityARemoved(Entity e) {
		e.getComponent(CTile.class).myTile.animationChange = true;
	}

}
