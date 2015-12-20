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
	 * The current color of the light (with randomization)
	 */
	public float[] currentRgb = new float[3];
	/**
	 * The brightness of the light; describes how many pixels it lasts before it's no longer visible
	 */
	public float brightness = 1;
	/**
	 * The current brightness of the light (with randomization)
	 */
	public float currentBrightness = 1;
	/**
	 * The position of the light
	 */
	public Vector2 position = new Vector2(0, 0);
	/**
	 * This field is used to return data to the render system; when querried, it will be populated with position and brightness.
	 */
	private float[] propertyArray = new float[3];
	/**
	 * Max amount of randomization to the distance of this light
	 */
	public float maxDistanceFlicker = 0;
	/**
	 * Max amount of randomization to the color of this light
	 */
	public float[] maxColorFlicker = new float[3];

	/**
	 * Gets the other three floats necessary for the GPU to render this.
	 * @return the property array passed by the render system to the GPU
	 */
	public float[] getPropertyArray(){
		propertyArray[0] = position.x;
		propertyArray[1] = position.y;
		propertyArray[2] = currentBrightness;
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
		return create(x, y, brightness, r, g, b, 0, 0, 0, 0);
	}

	/**
	 * Creates a new light with the given properties
	 * @param x the x-coordinate of the light
	 * @param y the y-coordinate of the light
	 * @param brightness the brigthness of the light
	 * @param r the red color in the light (between 0 and 1)
	 * @param g the green color in the light (between 0 and 1)
	 * @param b the blue color in the light (between 0 and 1)
	 * @param maxDistance the maximum variation in light distance (for flickering)
	 * @param maxColorR the max variation in red color (flicker)
	 * @param maxColorG the max variation in green color (flicker)
	 * @param maxColorB the max variation in blue color (flicker)
	 * @return the newly created light
	 */
	public static Light create(float x, float y, float brightness, float r, float g, float b, float maxDistance, float maxColorR, float maxColorG, float maxColorB){
		Light light = new Light();
		light.rgb = new float[]{
				r, g, b
		};
		light.brightness = brightness;
		light.position = new Vector2(x,y);
		light.maxColorFlicker = new float[]{
				maxColorR, maxColorG, maxColorB
		};
		light.maxDistanceFlicker = maxDistance;
		return light;
	}

	/**
	 * Updates the light's current colors with the flicker properties.
	 */
	public void updateFlicker(){
		currentRgb[0] += (maxColorFlicker[0] / -2) + (Math.random() * maxColorFlicker[0]);
		currentRgb[1] += (maxColorFlicker[1] / -2) + (Math.random() * maxColorFlicker[1]);
		currentRgb[2] += (maxColorFlicker[2] / -2) + (Math.random() * maxColorFlicker[2]);
		currentBrightness += (maxDistanceFlicker / -2) + (Math.random() * maxDistanceFlicker);

		if(currentRgb[0] < rgb[0] - maxColorFlicker[0]) currentRgb[0] = rgb[0] - maxColorFlicker[0];
		if(currentRgb[1] < rgb[1] - maxColorFlicker[1]) currentRgb[1] = rgb[1] - maxColorFlicker[1];
		if(currentRgb[2] < rgb[2] - maxColorFlicker[2]) currentRgb[2] = rgb[2] - maxColorFlicker[2];
		if(currentRgb[0] > rgb[0] + maxColorFlicker[0]) currentRgb[0] = rgb[0] + maxColorFlicker[0];
		if(currentRgb[1] > rgb[1] + maxColorFlicker[1]) currentRgb[1] = rgb[1] + maxColorFlicker[1];
		if(currentRgb[2] > rgb[2] + maxColorFlicker[2]) currentRgb[2] = rgb[2] + maxColorFlicker[2];
		if(currentBrightness < brightness - maxDistanceFlicker) currentBrightness = brightness - maxDistanceFlicker;
		if(currentBrightness > brightness + maxDistanceFlicker) currentBrightness = brightness + maxDistanceFlicker;
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
		set(x, y, brightness, r, g, b, 0, 0, 0, 0);
	}

	/**
	 * Sets the light's properties to the passed in ones. Meant to imitate the create method.
	 * @param x the x-coordinate of the light
	 * @param y the y-coordinate of the light
	 * @param brightness the brigthness of the light
	 * @param r the red color in the light (between 0 and 1)
	 * @param g the green color in the light (between 0 and 1)
	 * @param b the blue color in the light (between 0 and 1)
	 * @param maxDistance the max variation in distance (flicker)
	 * @param maxColorR the max variation in red color (flicker)
	 * @param maxColorG the max variation in green color (flicker)
	 * @param maxColorB the max variation in blue color (flicker)
	 */
	public void set(float x, float y, float brightness, float r, float g, float b, float maxDistance, float maxColorR, float maxColorG, float maxColorB){
		rgb[0] = r;
		rgb[1] = g;
		rgb[2] = b;
		this.brightness = brightness;
		position.set(x, y);
		maxColorFlicker[0] = maxColorR;
		maxColorFlicker[1] = maxColorG;
		maxColorFlicker[2] = maxColorB;
		maxDistanceFlicker = maxDistance;
	}

}
