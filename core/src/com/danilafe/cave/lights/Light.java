package com.danilafe.cave.lights;

import com.badlogic.gdx.math.Vector2;

/**
 * A class that holds data for a point light.
 * @author vanilla
 *
 */
public class Light {

	/**
	 * The color of the light, between 0 and 1
	 */
	public float[] rgb = new float[3];
	/**
	 * The brightness of the light; describes how many pixels it lasts before it's no longer visible
	 */
	public float brightness = 1;
	/**
	 * The position of the light
	 */
	public Vector2 position = new Vector2(0, 0);
	/**
	 * This field is used to return data to the render system; when querried, it will be populated with position and brightness.
	 */
	private float[] propertyArray = new float[3];

	/**
	 * Gets the other three floats necessary for the GPU to render this.
	 * @return the property array passed by the render system to the GPU
	 */
	public float[] getPropertyArray(){
		propertyArray[0] = position.x;
		propertyArray[1] = position.y;
		propertyArray[2] = brightness;
		return propertyArray;
	}

	/**
	 * Creates a new light with the given properties
	 * @param x the x-coordinate of the light
	 * @param y the y-coordinate of the light
	 * @param brightness the brigthness of the light
	 * @param r the red color in the light (between 0 and 1)
	 * @param g the green color in the light (between 0 and 1)
	 * @param b the blue color in the light (between 0 and 1)
	 * @return the newly created light
	 */
	public static Light create(float x, float y, float brightness, float r, float g, float b){
		Light light = new Light();
		light.rgb = new float[]{
				r, g, b
		};
		light.brightness = brightness;
		light.position = new Vector2(x,y);
		return light;
	}

	/**
	 * Sets the light's properties to the passed in ones. Meant to imitate the create method.
	 * @param x the x-coordinate of the light
	 * @param y the y-coordinate of the light
	 * @param brightness the brigthness of the light
	 * @param r the red color in the light (between 0 and 1)
	 * @param g the green color in the light (between 0 and 1)
	 * @param b the blue color in the light (between 0 and 1)
	 */
	public void set(float x, float y, float brightness, float r, float g, float b){
		rgb[0] = r;
		rgb[1] = g;
		rgb[2] = b;
		this.brightness = brightness;
		position.set(x, y);
	}

}
