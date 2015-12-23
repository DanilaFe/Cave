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
	 * The list of entities in the group this element belongs to. This corresponds to the element list.
	 */
	public ArrayList<Entity> entityList;
	/**
	 * The list of GroupElements in the same group as this one.
	 */
	public ArrayList<CGroupElement> elementList;
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
		elementList = null;
		prevKey = 0;
		nextKey = 0;
		selected = false;
		onSelect = null;
		maxDelta = 0;
		curDelta = 0;
	}

	public static ArrayList<Entity> createGroup(int prevKey, int nextKey, ECSRunnable onSelect, float maxDelta, Entity ...entities){
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		ArrayList<CGroupElement> groupElements = new ArrayList<CGroupElement>();
		for(Entity e : entities){
			entityList.add(e);
			CGroupElement groupElement = CaveGame.instance.pooledEngine.createComponent(CGroupElement.class);
			groupElements.add(groupElement);

			groupElement.entityList = entityList;
			groupElement.elementList = groupElements;
			groupElement.maxDelta = maxDelta;
			groupElement.nextKey = nextKey;
			groupElement.onSelect = onSelect;
			groupElement.prevKey = prevKey;
			groupElement.selected = false;

			e.add(groupElement);
		}
		groupElements.get(0).selected = true;
		return entityList;
	}

}
