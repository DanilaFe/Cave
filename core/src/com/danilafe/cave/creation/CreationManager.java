package com.danilafe.cave.creation;

import java.util.HashMap;

import com.danilafe.cave.animation.AnimationParameter;
import com.danilafe.cave.item.ItemParameter;
import com.danilafe.cave.tile.TileParameter;

/**
 * A class to store different creation parameters
 * @author vanilla
 *
 */
public class CreationManager {

	/**
	 * Entity descriptors mapped to strings, in order for easy retrieval.
	 */
	public HashMap<String, EntityDescriptor> entityDescriptors = new HashMap<String, EntityDescriptor>();
	/**
	 * Animation Parameters mapped to strings, in order for easy retrieval.
	 */
	public HashMap<String, AnimationParameter> animationParams = new HashMap<String, AnimationParameter>();
	/**
	 * Item Parameters mapped to strings, in order for easy retrieval.
	 */
	public HashMap<String, ItemParameter> itemParameters = new HashMap<String, ItemParameter>();
	/**
	 * The tile parameters representing different kinds of tiles.
	 */
	public HashMap<String, TileParameter> tileParameters = new HashMap<String, TileParameter>();

}
