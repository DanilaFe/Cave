package com.danilafe.cave.modifiers;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

/**
 * A wrapper class holding modifiers and some utility methods.
 * @author vanilla
 *
 */
public class ModifierContainer {

	/**
	 * A list that contains all the modifiers.
	 */
	public LinkedList<Modifier> modifierList = new LinkedList<Modifier>();
	/**
	 * The sum of the modifiers as it was last calculated.
	 */
	public Vector2 modifierSum = new Vector2(0, 0);
	
	public void removeAll(Modifier.ModifierType type){
		for(int i = modifierList.size() - 1; i > 0; i --){
			modifierList.remove(i);
		}
	}
}
