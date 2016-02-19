package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.attacks.WeaponDecriptor;

/**
 * Represents an entity that is wielding a weapon
 * @author vanilla
 *
 */
public class CWeaponWielding implements Poolable, Component {

	/**
	 * The weapon entity, if this entity is currently attacking
	 */
	public Entity weaponEntity = null;
	/**
	 * The descriptor for creating a new entity if one isn't present
	 */
	public WeaponDecriptor weaponData = null;

	@Override
	public void reset() {
		weaponEntity = null;
		weaponData = null;
	}

}
