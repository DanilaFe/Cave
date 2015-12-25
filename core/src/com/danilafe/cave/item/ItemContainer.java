package com.danilafe.cave.item;

import java.util.HashMap;

import com.danilafe.cave.runnable.ECSRunnable;

/**
 * A class describing a container for items.
 * @author vanilla
 *
 */
public class ItemContainer {

	/**
	 * The maximum number of different items.
	 */
	public int maxItems;
	/**
	 * The items in this container.
	 */
	public HashMap<ItemParameter, Integer> items = new HashMap<ItemParameter, Integer>();
	/**
	 * Called when this container is overflowed.
	 */
	public ECSRunnable onOverflow;

}
