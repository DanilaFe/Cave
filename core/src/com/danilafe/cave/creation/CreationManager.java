package com.danilafe.cave.creation;

import java.util.HashMap;

import com.danilafe.cave.animation.AnimationParameter;
import com.danilafe.cave.attacks.WeaponDecriptor;
import com.danilafe.cave.attacks.WeaponParameter;
import com.danilafe.cave.attacks.WeaponPropertiesCalculator;
import com.danilafe.cave.gui.GUITexture;
import com.danilafe.cave.item.ItemParameter;
import com.danilafe.cave.tile.TileAnimation;
import com.danilafe.cave.tile.TileParameter;
import com.danilafe.cave.world.WorldLoadParam;

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
	/**
	 * The tile animations representing different kinds of animations tiles can have
	 */
	public HashMap<String, TileAnimation> tileAnimations = new HashMap<String, TileAnimation>();
	/**
	 * The parameters of different weapons, such as their width, height, attack duration etc
	 */
	public HashMap<String, WeaponParameter> weaponParameters = new HashMap<String, WeaponParameter>();
	/**
	 * Different abstract classes used to calculate the properties of a weapon at a given time.
	 */
	public HashMap<String, WeaponPropertiesCalculator> weaponCalculators = new HashMap<String, WeaponPropertiesCalculator>();
	/**
	 * Descriptors for entire weapons, including their calculators, parameters and entity descriptors.
	 */
	public HashMap<String, WeaponDecriptor> weaponDescriptors = new HashMap<String, WeaponDecriptor>();
	/**
	 * Textures used when rendering GUI windows.
	 */
	public HashMap<String, GUITexture> guiTextures = new HashMap<String, GUITexture>();
	/**
	 * World load parameters used to load the world
	 */
	public HashMap<String, WorldLoadParam> worldLoadParams = new HashMap<String, WorldLoadParam>();

}
