package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Rectangle;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CNormalObject;
import com.danilafe.cave.ecs.components.CNormalObstacle;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;

public class NormalSystem extends FamilySystem {

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
			Rectangle projectedBounds = new Rectangle(normalEntityBounds.bounds);
			for(int j = 0; j < entitiesB.size(); j++){
				// We might have moved. Update projectedBounds and bounds
				normalEntityBounds.bounds.setCenter(normalEntityPosition.position.x, normalEntityPosition.position.y);
				projectedBounds.set(normalEntityBounds.bounds);
				
				// Check if we're already inside
				Entity obstacleEntity = entitiesB.get(j);
				CBounds obstacleBounds = obstacleEntity.getComponent(CBounds.class);
				CPosition obstaclePosition = obstacleEntity.getComponent(CPosition.class);
		
				if(obstacleBounds.bounds.overlaps(projectedBounds)){
					boolean boundsXBigger = obstacleBounds.bounds.x < projectedBounds.x;
					boolean boundsYBigger = obstacleBounds.bounds.y < projectedBounds.y;
					float minX = (boundsXBigger) ? obstacleBounds.bounds.x : projectedBounds.x;
					float minY = (boundsYBigger) ? obstacleBounds.bounds.y : projectedBounds.y;
					float maxX = (!boundsXBigger) ? obstacleBounds.bounds.x : projectedBounds.x;
					float maxY = (!boundsYBigger) ? obstacleBounds.bounds.y : projectedBounds.y;
					float wMin = (boundsXBigger) ? obstacleBounds.bounds.width : projectedBounds.width;
					float hMin = (boundsYBigger) ? obstacleBounds.bounds.height : projectedBounds.height;
					
					float rectWidth = wMin - (maxX - minX);
					float rectHeight = hMin - (maxY - minY);
					float rectX = maxX;
					float rectY = maxY;
					float xMutliplier = (rectX > projectedBounds.x) ? -1 : 1;
					float yMutliplier = (rectY > projectedBounds.y) ? -1 : 1;
					float smallerMovement = (rectWidth < rectHeight) ? rectWidth : rectHeight;
					normalEntityPosition.position.add(smallerMovement * xMutliplier, smallerMovement * yMutliplier);
				}	
								
				if(normalEntitySpeed != null) {
					// We might have moved...
					// Update position and the bounds.
					normalEntityBounds.bounds.setCenter(normalEntityPosition.position.x, normalEntityPosition.position.y);
					projectedBounds.set(normalEntityBounds.bounds);
					projectedBounds.setCenter(normalEntityPosition.position.cpy().add(normalEntitySpeed.speed.cpy().scl(deltaTime).x, 0));
					if(obstacleBounds.bounds.overlaps(projectedBounds)){
						boolean boundsXBigger = obstacleBounds.bounds.x < projectedBounds.x;
						boolean boundsYBigger = obstacleBounds.bounds.y < projectedBounds.y;
						float minX = (boundsXBigger) ? obstacleBounds.bounds.x : projectedBounds.x;
						float minY = (boundsYBigger) ? obstacleBounds.bounds.y : projectedBounds.y;
						float maxX = (!boundsXBigger) ? obstacleBounds.bounds.x : projectedBounds.x;
						float maxY = (!boundsYBigger) ? obstacleBounds.bounds.y : projectedBounds.y;
						float wMin = (boundsXBigger) ? obstacleBounds.bounds.width : projectedBounds.width;
						float hMin = (boundsYBigger) ? obstacleBounds.bounds.height : projectedBounds.height;
						
						float rectWidth = wMin - (maxX - minX);
						float rectHeight = hMin - (maxY - minY);
						float rectX = maxX;
						float rectY = maxY;
						float xMutliplier = (rectX > projectedBounds.x) ? -1 : 1;
						float yMutliplier = (rectY > projectedBounds.x) ? -1 : 1;
						float smallerMovement = (rectWidth < rectHeight) ? rectWidth : rectHeight;
						normalEntityPosition.position.add(smallerMovement * xMutliplier, smallerMovement * yMutliplier);
						normalEntitySpeed.speed.x = 0;
					}
					// We might have moved...
					// Update position and the bounds.
					normalEntityBounds.bounds.setCenter(normalEntityPosition.position.x, normalEntityPosition.position.y);
					projectedBounds.set(normalEntityBounds.bounds);
					projectedBounds.setCenter(normalEntityPosition.position.cpy().add(0, normalEntitySpeed.speed.cpy().scl(deltaTime).y));
					if(obstacleBounds.bounds.overlaps(projectedBounds)){
						boolean boundsXBigger = obstacleBounds.bounds.x < projectedBounds.x;
						boolean boundsYBigger = obstacleBounds.bounds.y < projectedBounds.y;
						float minX = (boundsXBigger) ? obstacleBounds.bounds.x : projectedBounds.x;
						float minY = (boundsYBigger) ? obstacleBounds.bounds.y : projectedBounds.y;
						float maxX = (!boundsXBigger) ? obstacleBounds.bounds.x : projectedBounds.x;
						float maxY = (!boundsYBigger) ? obstacleBounds.bounds.y : projectedBounds.y;
						float wMin = (boundsXBigger) ? obstacleBounds.bounds.width : projectedBounds.width;
						float hMin = (boundsYBigger) ? obstacleBounds.bounds.height : projectedBounds.height;
						
						float rectWidth = wMin - (maxX - minX);
						float rectHeight = hMin - (maxY - minY);
						float rectX = maxX;
						float rectY = maxY;
						float xMutliplier = (rectX > projectedBounds.x) ? -1 : 1;
						float yMutliplier = (rectY > projectedBounds.x) ? -1 : 1;
						float smallerMovement = (rectWidth < rectHeight) ? rectWidth : rectHeight;
						normalEntityPosition.position.add(smallerMovement * xMutliplier, smallerMovement * yMutliplier);
						normalEntitySpeed.speed.y = 0;
					}
				}				
				
			}
		}
	}

}
