package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.ecs.components.CBounds;

/**
 * DebugSystem - This system renders collision boxes of entities and other debug information to the screen.
 * @author vanilla
 *
 */
public class DebugRenderSystem extends IteratingSystem {

	/**
	 * The shape renderer used to render collision boxes.
	 */
	public ShapeRenderer shapeRenderer;

	/**
	 * Creates a new DebugRenderSystem
	 */
	public DebugRenderSystem() {
		super(Family.all(CBounds.class).get());
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CBounds entityBounds = entity.getComponent(CBounds.class);
		shapeRenderer.rect(entityBounds.bounds.x, entityBounds.bounds.y, entityBounds.bounds.width, entityBounds.bounds.height);
	}

	@Override
	public void update(float deltaTime) {
		if(CaveGame.instance.debug){
			shapeRenderer.setAutoShapeType(true);
			shapeRenderer.setProjectionMatrix(CaveGame.instance.orthoCam.combined);
			shapeRenderer.begin();
			super.update(deltaTime);
			shapeRenderer.end();
		}
	}

}
