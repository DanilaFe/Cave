package com.danilafe.cave;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
		pooledEngine = new PooledEngine(100, 100, 300, 300);
	}

	@Override
	public void render () {		
		/*
		 * Update the engine using the delta time 
		 */
		pooledEngine.update(Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * Shader function.
	 * This loads the GLSL shaders for the game.
	 */
	public static ShaderProgram loadShaders(String shaderName){
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
