package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.ecs.components.CLight;
import com.danilafe.cave.ecs.components.CPosition;

public class LightSystem extends IteratingSystem{

	public EntityListener lightListener;

	public LightSystem() {
		super(Family.all(CPosition.class, CLight.class).get());
		lightListener = new EntityListener() {

			@Override
			public void entityRemoved(Entity entity) {
				CaveGame.instance.renderSystem.lightManager.lights.remove(entity.getComponent(CLight.class).light);
			}

			@Override
			public void entityAdded(Entity entity) {
				CaveGame.instance.renderSystem.lightManager.lights.add(entity.getComponent(CLight.class).light);
			}
		};
		CaveGame.instance.pooledEngine.addEntityListener(Family.all(CLight.class).get(), lightListener);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CLight light = entity.getComponent(CLight.class);
		CPosition position = entity.getComponent(CPosition.class);
		light.light.position.set(light.offet.cpy().add(position.position));
	}

}
