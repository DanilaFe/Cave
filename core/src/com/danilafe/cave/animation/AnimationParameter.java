package com.danilafe.cave.animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * AnimationParameter - Defines the properties of an animation. 
 * There should be only one object of this type per each type of animation,
 * and for tracking the current frame etc an Animation has to be used.
 * @author vanilla
 *
 */
public class AnimationParameter {

	/**
	 * Array of frames
	 */
	public TextureRegion[][] textures;
	/**
	 * Whether this animation should loop or not
	 */
	public boolean loop = false;
	/**
	 * Milliseconds between each frame
	 */
	public float frameDelta;
	
}
