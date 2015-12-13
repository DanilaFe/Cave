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
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CFrictionCause;
import com.danilafe.cave.ecs.components.CFrictionObject;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CNormalObject;
import com.danilafe.cave.ecs.components.CNormalObstacle;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.systems.BoundsSystem;
import com.danilafe.cave.ecs.systems.DebugRenderSystem;
import com.danilafe.cave.ecs.systems.FrictionSystem;
import com.danilafe.cave.ecs.systems.GravitySystem;
import com.danilafe.cave.ecs.systems.NormalSystem;
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
	public NormalSystem normalSystem;
	public FrictionSystem frictionSystem;
	public OrthographicCamera orthoCam;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		
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
		normalSystem = new NormalSystem();
		frictionSystem = new FrictionSystem();
		
		orthoCam = new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		
		pooledEngine.addSystem(renderSystem);
		pooledEngine.addSystem(boundsSystem);
		pooledEngine.addSystem(debugRenderSystem);
		pooledEngine.addSystem(gravitySystem);
		pooledEngine.addSystem(normalSystem);
		pooledEngine.addSystem(positionSystem);
		pooledEngine.addSystem(frictionSystem);
		
		AnimationParameter placeholderAnimationParameter = new AnimationParameter();
		placeholderAnimationParameter.textures = TextureRegion.split(new Texture(Gdx.files.internal("badlogic.jpg")), 16, 16);
		placeholderAnimationParameter.loop = true;
		placeholderAnimationParameter.frameDelta = 1F / 5;
		Animation placeholderAnimation = new Animation();
		placeholderAnimation.animationParameter = placeholderAnimationParameter;
		
		Entity playerChar = pooledEngine.createEntity();
		CPosition position = pooledEngine.createComponent(CPosition.class);
		position.position.set(50, 50);
		CSpeed speed = pooledEngine.createComponent(CSpeed.class);
		speed.speed.set(10, 100);
		CAnimation animation = pooledEngine.createComponent(CAnimation.class);
		animation.animationQueue.animationQueue.add(placeholderAnimation);
		CBounds bounds = pooledEngine.createComponent(CBounds.class);
		bounds.bounds.set(50, 50, 16, 16);
		CGravity gravity = pooledEngine.createComponent(CGravity.class);
		gravity.gravity.set(0, -100);
		CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
		CFrictionObject frictionObject = pooledEngine.createComponent(CFrictionObject.class);
		playerChar.add(position);
		playerChar.add(speed);
		playerChar.add(animation);
		playerChar.add(bounds);
		playerChar.add(gravity);
		playerChar.add(normalObject);
		playerChar.add(frictionObject);
		pooledEngine.addEntity(playerChar);
		
		Entity firstWall = pooledEngine.createEntity();
		CPosition wallPos = pooledEngine.createComponent(CPosition.class);
		wallPos.position.set(50, 0);
		CBounds wallBds = pooledEngine.createComponent(CBounds.class);
		wallBds.bounds.set(50, 0, 300, 16);
		CNormalObstacle wallNob = pooledEngine.createComponent(CNormalObstacle.class);
		CFrictionCause wallFrc = pooledEngine.createComponent(CFrictionCause.class);
		wallFrc.frictionMultiplier.x = .1F;
		firstWall.add(wallPos);
		firstWall.add(wallBds);
		firstWall.add(wallNob);
		firstWall.add(wallFrc);
		pooledEngine.addEntity(firstWall);
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
