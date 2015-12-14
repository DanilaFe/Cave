package com.danilafe.cave.lights;

import com.badlogic.gdx.math.Vector2;

public class Light {

	public float[] rgb = new float[3];
	public float brightness = 1;
	public Vector2 position = new Vector2(0, 0);
	private float[] propertyArray = new float[3];
	
	public float[] getPropertyArray(){
		propertyArray[0] = position.x;
		propertyArray[1] = position.y;
		propertyArray[2] = brightness;
		return propertyArray;
	}
	
	public static Light create(float x, float y, float brightness, float r, float g, float b){
		Light light = new Light();
		light.rgb = new float[]{
				r, g, b
		};
		light.brightness = brightness;
		light.position = new Vector2(x,y);
		return light;
	}
	
}
