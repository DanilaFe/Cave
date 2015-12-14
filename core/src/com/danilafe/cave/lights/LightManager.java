package com.danilafe.cave.lights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

public class LightManager {

	public int maxLights = 16;
	public ArrayList<Light> lights = new ArrayList<Light>();
	
	public void sortByDistance(final Vector2 position){
		Collections.sort(lights, new Comparator<Light>() {
			@Override
			public int compare(Light o1, Light o2) {
				return (int) (position.dst(o1.position) - position.dst(o2.position));
			}
		});
	}
	
}
