package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CFollow;
import com.danilafe.cave.ecs.components.CPosition;

public class FollowingSystem extends IteratingSystem {

	public FollowingSystem() {
		super(Family.all(CFollow.class, CPosition.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CFollow follow = entity.getComponent(CFollow.class);
		CPosition position = entity.getComponent(CPosition.class);
		CPosition otherPos = follow.following.getComponent(CPosition.class);
		if(otherPos != null) position.position.set(otherPos.position.cpy().add(follow.offset));
	}

}