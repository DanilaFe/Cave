package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CNormalObject;
import com.danilafe.cave.ecs.components.CNormalObstacle;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;

/**
 * NormalSystem - Used for all things relating to the "normal force",
 * such as stopping entities before collision and separating clipping entities.
 * This operates only on entities with NormalObject and NormalObstacle flags.
 * @author vanilla
 *
 */
public class NormalSystem extends FamilySystem {

	/**
	 * Creates a new NormalSystem
	 */
	public NormalSystem() {
		super(Family.all(CNormalObject.class, CBounds.class).get(), Family.all(CNormalObstacle.class, CBounds.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for (int i = 0; i < entitiesA.size(); i++){
			Entity normalEntity = entitiesA.get(i);
			CBounds normalEntityBounds = normalEntity.getComponent(CBounds.class);
			CPosition normalEntityPosition = normalEntity.getComponent(CPosition.class);
			CSpeed normalEntitySpeed = normalEntity.getComponent(CSpeed.class);
			normalEntity.getComponent(CGravity.class);
			Rectangle projectedBounds = new Rectangle(normalEntityBounds.bounds);
			for(int j = 0; j < entitiesB.size(); j++){
				// We might have moved. Update projectedBounds and bounds
				normalEntityBounds.bounds.setCenter(normalEntityPosition.position.x, normalEntityPosition.position.y);
				projectedBounds.set(normalEntityBounds.bounds);

				// Check if we're already inside
				Entity obstacleEntity = entitiesB.get(j);
				CBounds obstacleBounds = obstacleEntity.getComponent(CBounds.class);
				obstacleEntity.getComponent(CPosition.class);

				if(obstacleBounds.bounds.overlaps(projectedBounds)){
					Gdx.app.debug("Collision Detection", "Intresection");
					moveOutside(projectedBounds, obstacleBounds.bounds, normalEntityPosition.position);
				}

				if(normalEntitySpeed != null) {
					// We might have moved...
					// Update position and the bounds.
					normalEntityBounds.bounds.setCenter(normalEntityPosition.position.x, normalEntityPosition.position.y);
					projectedBounds.set(normalEntityBounds.bounds);
					projectedBounds.setCenter(normalEntityPosition.position.cpy().add(normalEntitySpeed.speed.cpy().scl(deltaTime).x, 0));
					if(Math.abs(normalEntitySpeed.speed.x) > 0.01F && obstacleBounds.bounds.overlaps(projectedBounds)){
						Gdx.app.debug("Collision Detection", "Imminent collision on x-axis");
						normalEntityPosition.position.x = ((projectedBounds.x < obstacleBounds.bounds.x) ? obstacleBounds.bounds.x - projectedBounds.width : obstacleBounds.bounds.x + obstacleBounds.bounds.width) + (projectedBounds.width / 2);
						normalEntitySpeed.speed.x = 0;
					}
					// We might have moved...
					// Update position and the bounds.
					normalEntityBounds.bounds.setCenter(normalEntityPosition.position.x, normalEntityPosition.position.y);
					projectedBounds.set(normalEntityBounds.bounds);
					projectedBounds.setCenter(normalEntityPosition.position.cpy().add(0, normalEntitySpeed.speed.cpy().scl(deltaTime).y));
					if(Math.abs(normalEntitySpeed.speed.y) > 0.01F && obstacleBounds.bounds.overlaps(projectedBounds)){
						Gdx.app.debug("Collision Detection", "Imminent collision on y-axis");
						normalEntityPosition.position.y = ((projectedBounds.y < obstacleBounds.bounds.y) ? obstacleBounds.bounds.y - projectedBounds.height : obstacleBounds.bounds.y + obstacleBounds.bounds.height) + (projectedBounds.height / 2);
						normalEntitySpeed.speed.y = 0;
					}
				}

			}
		}
	}

	/**
	 * Movies the position provided in the direction to ensure the two given bounds don't overlap.
	 * @param movingBounds the bounds that will be moved.
	 * @param otherBounds the bounds that are static
	 * @param movingPosition the position of the moving bounds
	 */
	private void moveOutside(Rectangle movingBounds, Rectangle otherBounds, Vector2 movingPosition){
		boolean boundsXBigger = otherBounds.x < movingBounds.x;
		boolean boundsYBigger = otherBounds.y < movingBounds.y;
		float minX = (boundsXBigger) ? otherBounds.x : movingBounds.x;
		float minY = (boundsYBigger) ? otherBounds.y : movingBounds.y;
		float maxX = (!boundsXBigger) ? otherBounds.x : movingBounds.x;
		float maxY = (!boundsYBigger) ? otherBounds.y : movingBounds.y;
		float wMin = (boundsXBigger) ? otherBounds.width : movingBounds.width;
		float hMin = (boundsYBigger) ? otherBounds.height : movingBounds.height;

		float rectWidth = wMin - (maxX - minX);
		float rectHeight = hMin - (maxY - minY);
		float rectX = maxX;
		float rectY = maxY;
		float xMutliplier = (rectX > movingBounds.x) ? -1 : 1;
		float yMutliplier = (rectY > movingBounds.y) ? -1 : 1;
		if(rectWidth < rectHeight) movingPosition.x += rectWidth * xMutliplier;
		else movingPosition.y += rectHeight * yMutliplier;
		Gdx.app.debug("CollisionDetection: ", "rectWidth < rectHeight == " + Boolean.toString((rectWidth < rectHeight)));
	}

}
