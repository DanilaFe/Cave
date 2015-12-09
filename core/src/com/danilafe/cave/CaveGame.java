package com.danilafe.cave;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * CaveGame - Main class of Cave.
 * Used for all the GDX stuff, such as initialization and frames.
 * @author vanilla
 *
 */
public class CaveGame extends ApplicationAdapter {
	/**
	 * Ashley engine. This handles all the updates with the Entity / Component systems.
	 */
	public PooledEngine pooledEngine;
	
	@Override
	public void create () {
		/*
		 * Creation code
		 */
	}

	@Override
	public void render () {
		/*
		 * Clear the screen
		 */
		
		/*
		 * Update the engine using the delta time 
		 */
	}
	
	/**
	 * Shader function.
	 * This loads the GLSL shaders for the game.
	 */
	public ShaderProgram loadShaders(String shaderName){
		if(shaderName == null || shaderName.equals("") || !Gdx.files.internal("shaders/" + shaderName).exists()) return null;
		ShaderProgram.pedantic = false;
		ShaderProgram newProgram = new ShaderProgram(
				Gdx.files.internal("shaders/" + shaderName + "/vertex.glsl").readString(),
				Gdx.files.internal("shaders/" + shaderName + "/fragment.glsl").readString());
		if (!newProgram.isCompiled()) {
			System.out.println(newProgram.getLog());
			return null;
		}
		return newProgram;
	}
}
