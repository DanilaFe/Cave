package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.danilafe.cave.ecs.components.CBounds;

public class DebugRenderSystem extends IteratingSystem {

	public ShapeRenderer shapeRenderer;
	
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
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin();
		super.update(deltaTime);
		shapeRenderer.end();
	}

}
