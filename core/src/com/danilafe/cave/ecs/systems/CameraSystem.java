package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.ecs.components.CCameraShake;
import com.danilafe.cave.ecs.components.CCameraView;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CPosition;

/**
 * CameraSystem - makes cameras follow entities.
 * @author vanilla
 *
 */
public class CameraSystem extends FamilySystem {

	/**
	 * Creates a new CameraSystem
	 */
	public CameraSystem() {
		super(Family.all(CPosition.class, CCameraView.class).exclude(CDisabled.class).get(), Family.all(CCameraShake.class, CCameraView.class).exclude(CDisabled.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		for(Entity entity : entitiesB){
			CCameraShake cameraShake = entity.getComponent(CCameraShake.class);
			CCameraView cameraView = entity.getComponent(CCameraView.class);
			cameraShake.delay -= deltaTime;
			while(cameraShake.delay < 0 && cameraShake.distance > 0) {
				cameraShake.distance *= cameraShake.distanceDamping;
				cameraShake.currentOffset.set(cameraShake.nextOffset);
				if(cameraShake.distance < cameraShake.resetThreshold) {
					cameraShake.distance = 0;
				}
				do {
					cameraShake.nextOffset.set(1, 1).setLength(cameraShake.distance).setAngle((float) Math.random() * 360);
				} while (cameraShake.nextOffset.dst(cameraShake.currentOffset) < cameraShake.distance * .8f);
				cameraShake.delay += cameraShake.maxDelay;
			}
			if(cameraShake.maxDelay != 0 && cameraShake.distance > cameraShake.resetThreshold){
				Vector2 progress = cameraShake.nextOffset.cpy().sub(cameraShake.currentOffset).scl(1F - (cameraShake.delay / cameraShake.maxDelay));
				cameraView.shakeOffset.set(cameraShake.currentOffset.cpy().add(progress));
			} else {
				cameraView.shakeOffset.set(0, 0);
			}
		}

		for(Entity entity : entitiesA){
			CCameraView camView = entity.getComponent(CCameraView.class);
			CPosition position = entity.getComponent(CPosition.class);
			if(camView != null){
				if(position.position.x < camView.camera.position.x - camView.maxOffsetX) camView.truePosition.x = position.position.x + camView.maxOffsetX;
				if(position.position.x > camView.camera.position.x + camView.maxOffsetX) camView.truePosition.x = position.position.x - camView.maxOffsetX;
				if(position.position.y < camView.camera.position.y - camView.maxOffsetY) camView.truePosition.y = position.position.y + camView.maxOffsetY;
				if(position.position.y > camView.camera.position.y + camView.maxOffsetY) camView.truePosition.y = position.position.y - camView.maxOffsetY;
				camView.camera.position.set(camView.truePosition.x, camView.truePosition.y, 0).add(camView.shakeOffset.x, camView.shakeOffset.y, 0).add(camView.worldOffset.x, camView.worldOffset.y, 0);
				camView.camera.position.x = Math.round(camView.camera.position.x);
				camView.camera.position.y = Math.round(camView.camera.position.y);
				camView.camera.update();
			}
		}
	}

}
