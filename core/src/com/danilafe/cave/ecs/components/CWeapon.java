package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.attacks.Weapon;

/**
 * Represents an entity that is weapon
 * @author vanilla
 *
 */
public class CWeapon implements Poolable, Component {

	/**
	 * The weapon bound to this entity
	 */
	public Weapon weapon = null;

	@Override
	public void reset() {
		weapon = null;
	}

}
