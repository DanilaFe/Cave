package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.item.ItemContainer;

/**
 * ItemContainer component. Binds this entity to a container for items.
 * @author vanilla
 *
 */
public class CItemContainer implements Poolable, Component {

	/**
	 * The container this entity is bound to
	 */
	public ItemContainer container;

	@Override
	public void reset() {
		container = null;
	}

}
