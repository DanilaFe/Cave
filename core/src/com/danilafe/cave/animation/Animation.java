package com.danilafe.cave.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Animation - a class holding the current animation index.
 * This uses an AnimationParameter to get all the texture data etc.
 * @author vanilla
 *
 */
public class Animation {

	/**
	 * The animation data to be used, like the texture and the frame delta time.
	 */
	public AnimationParameter animationParameter;
	/**
	 * Index of current frame
	 */
	public int texIndex = 0;
	
	/**
	 * Gets the frame of the regular texture at the current index
	 * @param index - the index of the frame
	 * @return TextureRegion of the current frame.
	 */
	public TextureRegion getTextureAt(int index){
		return animationParameter.getTextureAt(index);
	}
	
	/**
	 * Gets the frame of the normal map at the current index
	 * @param index - the index of the frame
	 * @return TextureRegion of the current frame.
	 */
	public TextureRegion getNormalAt(int index){
		return animationParameter.getNormalAt(index);
	}
	
	
}
