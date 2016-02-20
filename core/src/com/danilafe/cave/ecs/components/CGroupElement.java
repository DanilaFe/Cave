package com.danilafe.cave.ecs.components;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * A component representing an entity that can be selected out of a group via using input (Keys).
 * @author vanilla
 *
 */
public class CGroupElement implements Poolable, Component {

	/**
	 * The list of entities in the group this element belongs to.
	 */
	public ArrayList<Entity> entityList;
	/**
	 * The input button to move to the previous selection
	 */
	public int prevKey = 0;
	/**
	 * The input button to move to the next selection
	 */
	public int nextKey = 0;
	/**
	 * Whether this component is selected.
	 */
	public boolean selected = false;
	/**
	 * Runnable to execute when this element is selected.
	 */
	public ECSRunnable onSelect;
	/**
	 * The delay between switching component
	 */
	public float maxDelta = 0;
	/**
	 * The current remaining delay before this element can switch.
	 */
	public float curDelta = 0;

	@Override
	public void reset() {
		entityList = null;
		prevKey = 0;
		nextKey = 0;
		selected = false;
		onSelect = null;
		maxDelta = 0;
		curDelta = 0;
	}

	/**
	 * Creates group elements from these entities, and returns the group.
	 * @param prevKey the key to select the previous element
	 * @param nextKey the key to select the next element
	 * @param onSelect runnable that executes when the element is selected
	 * @param maxDelta the delay between switches
	 * @param entities the entities to group
	 * @return the created group
	 */
	public static ArrayList<Entity> createGroup(int prevKey, int nextKey, ECSRunnable onSelect, float maxDelta, Entity ...entities){
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		for(Entity e : entities){
			entityList.add(e);
			CGroupElement groupElement = CaveGame.instance.pooledEngine.createComponent(CGroupElement.class);

			groupElement.entityList = entityList;
			groupElement.maxDelta = maxDelta;
			groupElement.nextKey = nextKey;
			groupElement.onSelect = onSelect;
			groupElement.prevKey = prevKey;
			groupElement.selected = false;

			e.add(groupElement);
		}
		entityList.get(0).getComponent(CGroupElement.class).selected = true;
		return entityList;
	}

}
