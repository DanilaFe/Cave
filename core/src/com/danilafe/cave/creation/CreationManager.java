package com.danilafe.cave.creation;

import java.util.HashMap;

import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.animation.AnimationParameter;

/**
 * A class to store different creation parameters
 * @author vanilla
 *
 */
public class CreationManager {

	public HashMap<String, EntityDescriptor> entityDescriptors = new HashMap<String, EntityDescriptor>();
	public HashMap<String, AnimationParameter> animationParams = new HashMap<String, AnimationParameter>();
	
}
