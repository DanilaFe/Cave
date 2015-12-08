package com.danilafe.cave.forces;

import com.badlogic.gdx.math.Vector2;

public class Force {

	public Vector2 forceDirector = new Vector2(0, 0);
	public ForceType forceType;
	
	enum ForceType {
		GRAVITY, // The force of gravity.
		NORMAL, // A force used to ensure entities don't push into blocks.
		INPUT // Force created by inputs
	}
	
}
