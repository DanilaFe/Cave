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
	
	public static AnimationParameter create(String texturePath, boolean loop, int texWidth, int texHeight, float frameDelta){
		AnimationParameter animationParameter = new AnimationParameter();
		animationParameter.textures = TextureRegion.split(CaveGame.instance.assetManager.get("textures/" + texturePath, Texture.class), texWidth, texHeight);
		animationParameter.normalTextures =  TextureRegion.split(CaveGame.instance.assetManager.get("normals/" + texturePath, Texture.class), texWidth, texHeight);
		animationParameter.frameDelta = frameDelta;
		animationParameter.loop = loop;
		return animationParameter;
	}
	
}
