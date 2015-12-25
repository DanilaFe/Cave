package com.danilafe.cave.item;

import com.danilafe.cave.animation.AnimationParameter;
import com.danilafe.cave.runnable.ECSRunnable;

/**
 * Class describing static properties of item.
 * @author vanilla
 *
 */
public class ItemParameter {

	/**
	 * The name of this item
	 */
	public String name;
	/**
	 * The description of this item
	 */
	public String description;
	/**
	 * What happens when this item is executed
	 */
	public ECSRunnable onUse;
	/**
	 * The animation parameter for this item.
	 */
	public AnimationParameter animation;

}
