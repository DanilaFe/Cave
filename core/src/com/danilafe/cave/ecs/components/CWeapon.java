package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.attacks.Weapon;

public class CWeapon implements Poolable, Component {

	public Weapon weapon = null;

	@Override
	public void reset() {
		weapon = null;
	}

}
