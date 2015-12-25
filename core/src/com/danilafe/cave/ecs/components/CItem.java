package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.item.ItemParameter;

/**
 * Item component - marks the entity is being an item (or a number of them)
 * @author vanilla
 *
 */
public class CItem implements Poolable, Component{

	/**
	 * The type of item this entity is
	 */
	public ItemParameter itemType;
	/**
	 * The number of items this entity represents
	 */
	public int itemCount = 1;

	@Override
	public void reset() {
		itemCount = 1;
		itemType = null;
	}

}
