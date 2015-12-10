package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CAnimation;

public class SpriteUpdateSystem extends IteratingSystem{

	public SpriteUpdateSystem() {
		super(Family.all(CAnimation.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		entity.getComponent(CAnimation.class).animationQueue.update(deltaTime);
	}

}
