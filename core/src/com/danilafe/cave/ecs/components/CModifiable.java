package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.modifiers.ModifierContainer;

/**
 * A Component that holds speed modifiers that have a constant effect on the displacement of the entity.
 * @author vanilla
 *
 */
public class CModifiable implements Poolable, Component{

	/**
	 * The container that holds all speed modifiers
	 */
	public ModifierContainer modifierContainer = new ModifierContainer();
	
	@Override
	public void reset() {
		modifierContainer = new ModifierContainer();
	}

}
