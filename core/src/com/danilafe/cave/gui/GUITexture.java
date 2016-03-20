package com.danilafe.cave.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.danilafe.cave.CaveGame;

/**
 * A texture data class for GUI Elements
 * @author vanilla
 *
 */
public class GUITexture {

	/**
	 * The nine regions of this texture
	 */
	public TextureRegion[][] textureRegion;

	/**
	 * Creates a new GUI  texture
	 * @param texture the texture to use for creation
	 * @return the produced GUI texture
	 */
	public static GUITexture create(Texture texture){
		return create(new TextureRegion(texture));
	}

	/**
	 * Creates a new GUI texture
	 * @param texture the texture region to use
	 * @return the produced GUITexture
	 */
	public static GUITexture create(TextureRegion texture){
		GUITexture newTexture = new GUITexture();
		newTexture.textureRegion = texture.split(texture.getRegionWidth() / 3, texture.getRegionHeight() / 3);
		return newTexture;
	}

	/**
	 * Creates a new GUI texture
	 * @param textureName the name of the texture file
	 * @return the produced texture
	 */
	public static GUITexture create(String textureName){
		return create(CaveGame.instance.assetManager.get("textures/" + textureName, Texture.class));
	}
}
