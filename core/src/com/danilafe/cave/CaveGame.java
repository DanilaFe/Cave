package com.danilafe.cave;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.animation.AnimationParameter;
import com.danilafe.cave.ecs.components.CAnimation;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.systems.BoundsSystem;
import com.danilafe.cave.ecs.systems.DebugRenderSystem;
import com.danilafe.cave.ecs.systems.GravitySystem;
import com.danilafe.cave.ecs.systems.PositionSystem;
import com.danilafe.cave.ecs.systems.RenderSystem;

/**
 * CaveGame - Main class of Cave.
 * Used for all the GDX stuff, such as initialization and frames.
 * @author vanilla
 *
 */
public class CaveGame extends ApplicationAdapter {
	public static CaveGame instance;
	/**
	 * Ashley engine. This handles all the updates with the Entity / Component systems.
	 */
	public PooledEngine pooledEngine;
	
	/**
	 * RenderSystem - renders stuff
	 */
	public RenderSystem renderSystem;
	public BoundsSystem boundsSystem;
	public DebugRenderSystem debugRenderSystem;
	public GravitySystem gravitySystem;
	public PositionSystem positionSystem;
	public OrthographicCamera orthoCam;
	
	
	@Override
	public void create () {
		/*
		 * Creation code
		 */
		instance = this;
		pooledEngine = new PooledEngine(100, 100, 300, 300);
		renderSystem = new RenderSystem();
		boundsSystem = new BoundsSystem();
		debugRenderSystem = new DebugRenderSystem();
		gravitySystem = new GravitySystem();
		positionSystem = new PositionSystem();
		
		orthoCam = new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		
		pooledEngine.addSystem(renderSystem);
		pooledEngine.addSystem(boundsSystem);
		pooledEngine.addSystem(debugRenderSystem);
		pooledEngine.addSystem(gravitySystem);
		pooledEngine.addSystem(positionSystem);
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
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		orthoCam.setToOrtho(false, Constants.CAMERA_WIDTH, Constants.CAMERA_WIDTH * height / width);
	}
}
