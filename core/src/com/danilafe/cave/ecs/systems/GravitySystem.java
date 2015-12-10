package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CSpeed;

public class GravitySystem extends IteratingSystem{

	public GravitySystem() {
		super(Family.all(CSpeed.class, CGravity.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CGravity entityGravity = entity.getComponent(CGravity.class);
		CSpeed entitySpeed = entity.getComponent(CSpeed.class);
		entitySpeed.speed.add(entityGravity.gravity.cpy().scl(deltaTime));
	}

}
