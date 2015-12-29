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
	 * Creates an animation parameter from a region of the given texture
	 * @param texturePath the texture to use
	 * @param regionX the x-coordinate of the region
	 * @param regionY the y-coordinate of the region
	 * @param regionWidth the width of the region
	 * @param regionHeight the height of the region
	 * @param loop whether this animation should loop
	 * @param texWidth the width of a single frame
	 * @param texHeight the height of the single frame
	 * @param frameDelta the delta time between frames
	 * @return the newly created AnomationParameter
	 */
	public static AnimationParameter create(String texturePath, int regionX, int regionY, int regionWidth, int regionHeight, boolean loop, int texWidth, int texHeight, float frameDelta){
		AnimationParameter animationParameter = new AnimationParameter();
		TextureRegion sourceTexture = new TextureRegion(CaveGame.instance.assetManager.get("textures/" + texturePath, Texture.class));
		sourceTexture.setRegion(regionX, regionY, regionWidth, regionHeight);
		animationParameter.textures = sourceTexture.split(texWidth, texHeight);
		animationParameter.frameDelta = frameDelta;
		animationParameter.loop = loop;
		if(CaveGame.instance.assetManager.isLoaded("normals/" + texturePath)) {
			TextureRegion sourceNormal = new TextureRegion(CaveGame.instance.assetManager.get("textures/" + texturePath, Texture.class));
			sourceNormal.setRegion(regionX, regionY, regionWidth, regionHeight);
			animationParameter.normalTextures = sourceNormal.split(texWidth, texHeight);
		}
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
