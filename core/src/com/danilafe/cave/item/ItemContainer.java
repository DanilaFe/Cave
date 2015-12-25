package com.danilafe.cave.item;

import java.util.HashMap;

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

}
