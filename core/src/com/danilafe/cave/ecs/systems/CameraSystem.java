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
			if(position.position.x < camView.camera.position.x - camView.maxOffsetX) camView.camera.position.x = position.position.x + camView.maxOffsetX;
			if(position.position.x > camView.camera.position.x + camView.maxOffsetX) camView.camera.position.x = position.position.x - camView.maxOffsetX;
			if(position.position.y < camView.camera.position.y - camView.maxOffsetY) camView.camera.position.y = position.position.y + camView.maxOffsetY;
			if(position.position.y > camView.camera.position.y + camView.maxOffsetY) camView.camera.position.y = position.position.y - camView.maxOffsetY;
			camView.camera.position.add(camView.offset.x, camView.offset.y, 0);
			camView.camera.update();
		}
	}

}
