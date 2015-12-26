package com.danilafe.cave.animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.danilafe.cave.CaveGame;

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
	 * Normal maps associated with this animation
	 */
	public TextureRegion[][] normalTextures;
	/**
	 * Whether this animation should loop or not
	 */
	public boolean loop = false;
	/**
	 * Milliseconds between each frame
	 */
	public float frameDelta;

	/**
	 * Creates a new animation parameter from the given arguments.
	 * @param texturePath The path to the texture load from
	 * @param loop whether or not the animation should loop
	 * @param texWidth the width of the sub frames
	 * @param texHeight the height of the sub frames
	 * @param frameDelta the delta time between frames
	 * @return the newly created AnimationParameter
	 */
	public static AnimationParameter create(String texturePath, boolean loop, int texWidth, int texHeight, float frameDelta){
		AnimationParameter animationParameter = new AnimationParameter();
		animationParameter.textures = TextureRegion.split(CaveGame.instance.assetManager.get("textures/" + texturePath, Texture.class), texWidth, texHeight);
		if(CaveGame.instance.assetManager.isLoaded("normals/" + texturePath))
			animationParameter.normalTextures =  TextureRegion.split(CaveGame.instance.assetManager.get("normals/" + texturePath, Texture.class), texWidth, texHeight);
		animationParameter.frameDelta = frameDelta;
		animationParameter.loop = loop;
		return animationParameter;
	}

	/**
	 * Gets the texture region of the regular texture at the given index.
	 * @param index the index of the region, beginning at 0 and increasing left to right, and down to up
	 * @return the texture region at the given location
	 */
	public TextureRegion getTextureAt(int index){
		return textures[index / textures[0].length][index % textures[0].length];
	}

	/**
	 * Gets the texture region of the normal map at the given index
	 * @param index index the index of the image, beginning at 0 and increasing left to right, and down to up
	 * @return the texture region at the given location
	 */
	public TextureRegion getNormalAt(int index){
		return normalTextures[index / normalTextures[0].length][index % normalTextures[0].length];
	}
}
