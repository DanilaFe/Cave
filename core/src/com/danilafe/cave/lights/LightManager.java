package com.danilafe.cave.lights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;

/**
 * Class used in order to manage sources of light by the render system.
 * @author vanilla
 *
 */
public class LightManager {

	/**
	 * The maximum number of lights on the screen.
	 */
	public int maxLights = 64;
	/**
	 * The list of lights to be rendered
	 */
	public ArrayList<Light> lights = new ArrayList<Light>();

	/**
	 * Sorts the lights in this manager by how far they are from the position. This is used in order to limit the lights rendered.
	 * @param position the center from which distance to the light is taken.
	 */
	public void sortByDistance(final Vector2 position){
		Collections.sort(lights, new Comparator<Light>() {
			@Override
			public int compare(Light o1, Light o2) {
				return Float.compare(position.dst(o1.position),  position.dst(o2.position));
			}
		});
	}

}
