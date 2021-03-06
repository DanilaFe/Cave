package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CGroupElement;

/**
 * SelectedElementSystem - used to update the selected elements in their own groups.
 * @author vanilla
 *
 */
public class SelectableElementSystem extends IteratingSystem {

	/**
	 * Creates a new SelectableElementSystem.
	 */
	public SelectableElementSystem() {
		super(Family.all(CGroupElement.class).exclude(CDisabled.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CGroupElement groupElement = entity.getComponent(CGroupElement.class);
		int currentIndex = groupElement.entityList.indexOf(entity);
		int newIndex = currentIndex;
		groupElement.curDelta -= deltaTime;
		if(groupElement.curDelta < 0) groupElement.curDelta = 0;

		if(groupElement.curDelta == 0 && groupElement.selected){
			boolean nextPressed = Gdx.input.isKeyPressed(groupElement.nextKey);
			boolean prevPressed = Gdx.input.isKeyPressed(groupElement.prevKey);
			if(nextPressed && prevPressed) return;
			CGroupElement newElement = null;
			if(nextPressed && currentIndex != groupElement.entityList.size() - 1){
				newElement = groupElement.entityList.get(++newIndex).getComponent(CGroupElement.class);

			}
			else if(prevPressed && groupElement.entityList.indexOf(entity) != 0){
				newElement = groupElement.entityList.get(--newIndex).getComponent(CGroupElement.class);
			}
			if(newElement == null) return;

			newElement.curDelta = newElement.maxDelta;
			newElement.selected = true;
			if(newElement.onSelect != null) newElement.onSelect.update(groupElement.entityList.get(newIndex), deltaTime);
			groupElement.selected = false;
			if(groupElement.onDeselect != null) groupElement.onDeselect.update(entity, deltaTime);
		}
	}

}
