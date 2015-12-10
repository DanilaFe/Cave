package com.danilafe.cave.modifiers;

import com.badlogic.gdx.math.Vector2;

/**
 * Modifiers have a constant effect on the object's speed.
 * They don't affect the speed variable.
 * @author vanilla
 *
 */
public class Modifier {
	/**
	 * The direction of the modifier
	 */
	public Vector2 modDirection = new Vector2(0, 0);
	/**
	 * The type of the modifier
	 */
	public ModifierType modType;
	/**
	 * The ID of this modifier.
	 */
	public int id = 0;
	
	public enum ModifierType {
		INPUT // Force created by inputs
	}
	
}
