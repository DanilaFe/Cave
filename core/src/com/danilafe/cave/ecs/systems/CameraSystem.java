package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CCameraView;
import com.danilafe.cave.ecs.components.CPosition;

/**
 * CameraSystem - makes cameras follow entities.
 * @author vanilla
 *
 */
public class CameraSystem extends IteratingSystem {

	/**
	 * Creates a new CameraSystem
	 */
	public CameraSystem() {
		super(Family.all(CPosition.class, CCameraView.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CCameraView camView = entity.getComponent(CCameraView.class);
		CPosition position = entity.getComponent(CPosition.class);
		if(camView != null){
			camView.camera.position.set(position.position, 0);
			camView.camera.update();
		}
	}

}
