package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CItem;
import com.danilafe.cave.ecs.components.CItemContainer;

public class ItemSystem extends FamilySystem {

	public ItemSystem() {
		super(Family.all(CItem.class).exclude(CDisabled.class).get(), Family.all(CItemContainer.class).exclude(CDisabled.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		for(Entity container : entitiesB){
			CItemContainer itemContainer = container.getComponent(CItemContainer.class);
			if(itemContainer.container.items.size() > itemContainer.container.maxItems) itemContainer.container.onOverflow.update(container, deltaTime);
		}
	}

}
