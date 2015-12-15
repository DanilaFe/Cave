package com.danilafe.cave;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.animation.AnimationParameter;
import com.danilafe.cave.creation.CreationManager;
import com.danilafe.cave.creation.EntityDescriptor;
import com.danilafe.cave.ecs.components.CAnimation;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CCameraView;
import com.danilafe.cave.ecs.components.CFrictionCause;
import com.danilafe.cave.ecs.components.CFrictionObject;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CNormalObject;
import com.danilafe.cave.ecs.components.CNormalObstacle;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.components.CStepper;
import com.danilafe.cave.ecs.systems.BoundsSystem;
import com.danilafe.cave.ecs.systems.CameraSystem;
import com.danilafe.cave.ecs.systems.DebugRenderSystem;
import com.danilafe.cave.ecs.systems.FrictionSystem;
import com.danilafe.cave.ecs.systems.GravitySystem;
import com.danilafe.cave.ecs.systems.NormalSystem;
import com.danilafe.cave.ecs.systems.PositionSystem;
import com.danilafe.cave.ecs.systems.RenderSystem;
import com.danilafe.cave.ecs.systems.StepperSystem;
import com.danilafe.cave.runnable.ECSRunnable;

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
	/**
	 * BoundsSystem - updates bounds to position
	 */
	public BoundsSystem boundsSystem;
	/**
	 * DebugRenderSystem - renders collision boxes and debug info
	 */
	public DebugRenderSystem debugRenderSystem;
	/**
	 * GravitySystem - ..gravity
	 */
	public GravitySystem gravitySystem;
	/**
	 * PositionSystem - updates position based on speed
	 */
	public PositionSystem positionSystem;
	/**
	 * NormalSystem - stops intersections and clipping 
	 */
	public NormalSystem normalSystem;
	/**
	 * FrictionSystem - applies friction
	 */
	public FrictionSystem frictionSystem;
	/**
	 * StepperSystem - steps all custom code entities.
	 */
	public StepperSystem stepperSystem;
	public CameraSystem cameraSystem;
	/**
	 * Camera used to look into the game world.
	 */
	public OrthographicCamera orthoCam;
	public CreationManager creationManager;
	public AssetManager assetManager;
	
	@Override
	public void create () {
		// Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		
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
		stepperSystem = new StepperSystem();
		cameraSystem = new CameraSystem();
		
		orthoCam = new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		
		pooledEngine.addSystem(renderSystem);
		pooledEngine.addSystem(boundsSystem);
		pooledEngine.addSystem(debugRenderSystem);
		pooledEngine.addSystem(gravitySystem);
		pooledEngine.addSystem(normalSystem);
		pooledEngine.addSystem(positionSystem);
		pooledEngine.addSystem(frictionSystem);
		pooledEngine.addSystem(stepperSystem);
		pooledEngine.addSystem(cameraSystem);
		
		assetManager = new AssetManager();
		creationManager = new CreationManager();
		
		loadAssets();
		loadCreation();
		
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderPlayer").create(50, 50));
		for(int i = 0; i < 10; i ++){
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(16 * i, 0));
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(0, 16 * (i + 1)));
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(96, 16 * (i + 1)));
		}
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(16, 16 * 2));
	}

	private void loadCreation() {
		/*
		 * Animations
		 */
		creationManager.animationParams.put("placeholder", AnimationParameter.create("tests/balltest.png", true, 16, 16, 1F / 5));
		
		/*
		 * Entities
		 */
		EntityDescriptor playerholderPlayer = new EntityDescriptor() {	
			@Override
			public Entity create(float x, float y) {
				Animation placeholderAnimation = new Animation();
				placeholderAnimation.animationParameter = creationManager.animationParams.get("placeholder");
				
				Entity entity = pooledEngine.createEntity();
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CSpeed speed = pooledEngine.createComponent(CSpeed.class);
				speed.speed.set(10, 100);
				CAnimation animation = pooledEngine.createComponent(CAnimation.class);
				animation.animationQueue.animationQueue.add(placeholderAnimation);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.set(x, y, 16, 16);
				CGravity gravity = pooledEngine.createComponent(CGravity.class);
				gravity.gravity.set(0, -500);
				CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
				CFrictionObject frictionObject = pooledEngine.createComponent(CFrictionObject.class);
				CStepper stepper = pooledEngine.createComponent(CStepper.class);
				stepper.runnable = new ECSRunnable() {	
					@Override
					public void update(Entity me, Engine myEngine, float deltaTime) {
						CSpeed mySpeed = me.getComponent(CSpeed.class);
						CFrictionObject myFriction = me.getComponent(CFrictionObject.class);
						CBounds myBounds = me.getComponent(CBounds.class);
						CGravity myGravit = me.getComponent(CGravity.class);
						boolean enableFriction = true;
						if(Gdx.input.isKeyPressed(Keys.RIGHT) && Math.abs(mySpeed.speed.x) < 75 ) {
							mySpeed.speed.x += 100F * deltaTime;
							enableFriction = false;
						}
						if(Gdx.input.isKeyPressed(Keys.LEFT) && Math.abs(mySpeed.speed.x) < 75) {
							mySpeed.speed.x -= 100F * deltaTime;
							enableFriction = false;
						}
						if(Gdx.input.isKeyPressed(Keys.SPACE) && checkNormalCollision(new Rectangle(myBounds.bounds).setPosition(myBounds.bounds.x + myGravit.gravity.x * deltaTime, myBounds.bounds.y + myGravit.gravity.y * deltaTime))){
							mySpeed.speed.y = 100; 
						}
						myFriction.frictionCoefficient.x = (enableFriction) ? 1 : 0;
					}
				};
				CCameraView camView = pooledEngine.createComponent(CCameraView.class);
				camView.camera = orthoCam;
				entity.add(camView);
				entity.add(position);
				entity.add(speed);
				entity.add(animation);
				entity.add(bounds);
				entity.add(gravity);
				entity.add(normalObject);
				entity.add(frictionObject);
				entity.add(stepper);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderPlayer", playerholderPlayer);
		EntityDescriptor placeholderWall = new EntityDescriptor() {
			
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.set(x, y, 16, 16);
				CNormalObstacle normalObstacle = pooledEngine.createComponent(CNormalObstacle.class);
				CFrictionCause frictionCause = pooledEngine.createComponent(CFrictionCause.class);
				frictionCause.frictionMultiplier.x = .01F;
				entity.add(position);
				entity.add(bounds);
				entity.add(normalObstacle);
				entity.add(frictionCause);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderWall", placeholderWall);
	}

	private void loadAssets() {
		assetManager.load("textures/tests/balltest.png", Texture.class);
		assetManager.load("normals/tests/balltest.png", Texture.class);
		while(!assetManager.update());
	}

	@Override
	public void render () {		
		/*
		 * Update the engine using the delta time 
		 */
		pooledEngine.update(Gdx.graphics.getDeltaTime());
		Gdx.app.debug("FPS", Gdx.graphics.getFramesPerSecond() + " Frames Per Second");
	}
	
	/**
	 * Shader function.
	 * This loads the GLSL shaders for the game.
	 * @param shaderName the name of the shader to load.
	 * @return the loaded and compiled shader program, or null of compilation failed.
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
	
	public boolean checkNormalCollision(Rectangle rect){
		for (Entity e : normalSystem.entitiesB){
			if(e.getComponent(CBounds.class).bounds.overlaps(rect)) return true;
		}
		return false;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		orthoCam.setToOrtho(false, Constants.CAMERA_WIDTH, Math.round(Constants.CAMERA_WIDTH * height / width));
	}
}
