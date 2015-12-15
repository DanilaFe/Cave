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
	 * Gets the frame at the current index
	 * @param index - the index of the frame
	 * @return TextureRegion of the current frame.
	 */
	public TextureRegion getTextureAt(int index){
		return animationParameter.textures[index / animationParameter.textures[0].length][index % animationParameter.textures[0].length];
	}
	
	public TextureRegion getNormalAt(int index){
		return animationParameter.normalTextures[index / animationParameter.normalTextures[0].length][index % animationParameter.normalTextures[0].length];
	}
	
}
