package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.danilafe.cave.ecs.components.CFollow;
import com.danilafe.cave.ecs.components.CPosition;

/**
 * FollowingSystem - in charge of updating the positions of the Following entities.
 * @author vanilla
 *
 */
public class FollowingSystem extends IteratingSystem {

	/**
	 * Creates a new FollowingSystem
	 */
	public FollowingSystem() {
		super(Family.all(CFollow.class, CPosition.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		ImmutableArray<Entity> entities = getEntities();
		Array<Entity> toProcess = new Array<Entity>();
		for(int i = 0; i < entities.size(); i++){
			toProcess.add(entities.get(i));
		}

		while(toProcess.size > 0){
			recursiveFollow(toProcess.get(0), toProcess);
		}
	}

	public void recursiveFollow(Entity e, Array<Entity> removeFrom){
		CFollow follow = e.getComponent(CFollow.class);
		if(getFamily().matches(follow.following)) recursiveFollow(follow.following, removeFrom);
		e.getComponent(CPosition.class).position.set(follow.following.getComponent(CPosition.class).position.cpy().add(follow.offset));
		removeFrom.removeValue(e, false);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) { }

}
