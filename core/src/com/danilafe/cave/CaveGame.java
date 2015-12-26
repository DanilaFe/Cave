package com.danilafe.cave;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.danilafe.cave.creation.CreationManager;
import com.danilafe.cave.creation.EntityDescriptor;
import com.danilafe.cave.ecs.components.CAcceleration;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CCameraView;
import com.danilafe.cave.ecs.components.CFrictionCause;
import com.danilafe.cave.ecs.components.CFrictionObject;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CInteractionCause;
import com.danilafe.cave.ecs.components.CInteractive;
import com.danilafe.cave.ecs.components.CLight;
import com.danilafe.cave.ecs.components.CMarked;
import com.danilafe.cave.ecs.components.CNormalObject;
import com.danilafe.cave.ecs.components.CNormalObstacle;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.components.CStepper;
import com.danilafe.cave.ecs.systems.AccelerationSystem;
import com.danilafe.cave.ecs.systems.BoundsSystem;
import com.danilafe.cave.ecs.systems.CameraSystem;
import com.danilafe.cave.ecs.systems.DebugRenderSystem;
import com.danilafe.cave.ecs.systems.FollowingSystem;
import com.danilafe.cave.ecs.systems.FrictionSystem;
import com.danilafe.cave.ecs.systems.GravitySystem;
import com.danilafe.cave.ecs.systems.InteractionSystem;
import com.danilafe.cave.ecs.systems.LightSystem;
import com.danilafe.cave.ecs.systems.NormalSystem;
import com.danilafe.cave.ecs.systems.PositionSystem;
import com.danilafe.cave.ecs.systems.RenderSystem;
import com.danilafe.cave.ecs.systems.SelectableElementSystem;
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
	/**
	 * CameraSystem - updates the position of the light
	 */
	public CameraSystem cameraSystem;
	/**
	 * LightSystem - handles moving lights attached to entities.
	 */
	public LightSystem lightSystem;
	/**
	 * AccelerationSystem - accelerate things
	 */
	public AccelerationSystem accelerationSystem;
	/**
	 * SelectableSystem - update selectable stuff.
	 */
	public SelectableElementSystem selectableElementSytem;
	/**
	 * FollowingSystem - makes stuff follow other stuff
	 */
	public FollowingSystem followingSystem;
	/**
	 * InteractionSystem - handles interactions
	 */
	public InteractionSystem interactionSystem;
	/**
	 * Camera used to look into the game world.
	 */
	public OrthographicCamera orthoCam;
	/**
	 * Creation Manager used to load previously created data
	 */
	public CreationManager creationManager;
	/**
	 * The asset manager used to load textures
	 */
	public AssetManager assetManager;
	/**
	 * Whether the game is in debug mode.
	 */
	public boolean debug = Constants.DEBUG;
	/**
	 * Whether the collision boxes should be rendered (when debugging)
	 */
	public boolean collisionBoxes = true;
	/**
	 * Whether the debug output should be allowed (when debugging)
	 */
	public boolean debugOutput = false;

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
		normalSystem = new NormalSystem();
		frictionSystem = new FrictionSystem();
		stepperSystem = new StepperSystem();
		cameraSystem = new CameraSystem();
		lightSystem = new LightSystem();
		accelerationSystem = new AccelerationSystem();
		selectableElementSytem = new SelectableElementSystem();
		followingSystem = new FollowingSystem();
		interactionSystem = new InteractionSystem();

		orthoCam = new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

		pooledEngine.addSystem(renderSystem);
		pooledEngine.addSystem(boundsSystem);
		pooledEngine.addSystem(debugRenderSystem);
		pooledEngine.addSystem(gravitySystem);
		pooledEngine.addSystem(interactionSystem);
		pooledEngine.addSystem(normalSystem);
		pooledEngine.addSystem(positionSystem);
		pooledEngine.addSystem(frictionSystem);
		pooledEngine.addSystem(stepperSystem);
		pooledEngine.addSystem(cameraSystem);
		pooledEngine.addSystem(lightSystem);
		pooledEngine.addSystem(accelerationSystem);
		pooledEngine.addSystem(selectableElementSytem);
		pooledEngine.addSystem(followingSystem);

		assetManager = new AssetManager();
		creationManager = new CreationManager();

		loadAssets();
		loadCreation();

		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderPlayer").create(50, 50));
		for(int i = 0; i < 10; i ++){
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(8 * i, 0));
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(0, 8 * (i + 1)));
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(72, 8 * (i + 1)));
		}
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderWall").create(8, 8 * 2));
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderJumpBoost").create(64, 8));

		for(int i = 0; i < 32; i ++){
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderLightball").create((float) Math.random() * 80, (float) Math.random() * 50));
		}

	}

	private void loadCreation() {
		/*
		 * Entities
		 */
		EntityDescriptor playerholderPlayer = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CSpeed speed = pooledEngine.createComponent(CSpeed.class);
				speed.speed.set(10, 100);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.set(x, y, 8, 8);
				CGravity gravity = pooledEngine.createComponent(CGravity.class);
				gravity.gravity.set(0, Constants.DEFAULT_GRAVITY);
				CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
				CFrictionObject frictionObject = pooledEngine.createComponent(CFrictionObject.class);
				CStepper stepper = pooledEngine.createComponent(CStepper.class);
				stepper.runnable = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						CSpeed mySpeed = me.getComponent(CSpeed.class);
						CFrictionObject myFriction = me.getComponent(CFrictionObject.class);
						CBounds myBounds = me.getComponent(CBounds.class);
						boolean enableFriction = true;
						if(Gdx.input.isKeyPressed(Keys.RIGHT) && Math.abs(mySpeed.speed.x) < 75 ) {
							mySpeed.speed.x += 100F * deltaTime;
							enableFriction = false;
						}
						if(Gdx.input.isKeyPressed(Keys.LEFT) && Math.abs(mySpeed.speed.x) < 75) {
							mySpeed.speed.x -= 100F * deltaTime;
							enableFriction = false;
						}
						if(Gdx.input.isKeyPressed(Keys.SPACE) && normalSystem.checkNormalEdge(2, myBounds.bounds)){
							mySpeed.speed.y += 70;
						}
						myFriction.frictionCoefficient.x = (enableFriction) ? 1 : 0;
					}
				};
				CCameraView camView = pooledEngine.createComponent(CCameraView.class);
				camView.camera = orthoCam;
				CInteractionCause interactionCause = pooledEngine.createComponent(CInteractionCause.class);
				interactionCause.canInteract = true;
				entity.add(interactionCause);
				camView.maxOffsetX = Constants.CAMERA_WIDTH / 8;
				camView.maxOffsetY = Constants.CAMERA_WIDTH / 12;
				entity.add(camView);
				entity.add(position);
				entity.add(speed);
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
				bounds.bounds.set(x, y, 8, 8);
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
		EntityDescriptor lightball = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CLight light = pooledEngine.createComponent(CLight.class);
				light.light.set(0, 0, 10, .1F, .3F, .3F);
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CSpeed speed = pooledEngine.createComponent(CSpeed.class);
				CStepper stepper =  pooledEngine.createComponent(CStepper.class);
				CMarked mark = pooledEngine.createComponent(CMarked.class);
				mark.marks.put("floater", true);
				stepper.runnable = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						CBounds myBounds = me.getComponent(CBounds.class);
						CSpeed mySpeed = me.getComponent(CSpeed.class);
						for(Entity e : pooledEngine.getEntitiesFor(Family.all(CPosition.class, CSpeed.class, CBounds.class).get())){
							if(Family.all(CMarked.class).get().matches(e) && e.getComponent(CMarked.class).marks.get("floater").booleanValue()) continue;
							CBounds otherBounds = e.getComponent(CBounds.class);
							CSpeed otherSpeed = e.getComponent(CSpeed.class);
							if(myBounds.bounds.overlaps(otherBounds.bounds)){
								mySpeed.speed.add(otherSpeed.speed.cpy().scl(deltaTime).scl(.1F));
							}
						}
					}
				};
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.setSize(5, 5);
				CAcceleration acceleration = pooledEngine.createComponent(CAcceleration.class);
				acceleration.scalarAcceleration.set(.8F, .8F);
				CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
				CGravity gravity = pooledEngine.createComponent(CGravity.class);
				gravity.gravity.set(0, -0.1F);
				entity.add(gravity);
				entity.add(mark);
				entity.add(bounds);
				entity.add(stepper);
				entity.add(speed);
				entity.add(position);
				entity.add(light);
				entity.add(acceleration);
				entity.add(normalObject);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderLightball", lightball);
		EntityDescriptor jumpBoost = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity newEntity = creationManager.entityDescriptors.get("placeholderWall").create(x, y);
				CLight light = pooledEngine.createComponent(CLight.class);
				light.light.set(x, y, 16, .5F, .5F, .5F, 4, .5F, .5F, .5F);
				CInteractive interactive = pooledEngine.createComponent(CInteractive.class);
				interactive.interactKey = Keys.SPACE;
				interactive.onInteract = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						CInteractive myInteractive = me.getComponent(CInteractive.class);
						CSpeed otherSpeed = myInteractive.currentInteraction.getComponent(CSpeed.class);
						CBounds myBounds = me.getComponent(CBounds.class);
						CBounds otherBounds = myInteractive.currentInteraction.getComponent(CBounds.class);
						if(Utils.checkEdgeContact(0, myBounds.bounds, otherBounds.bounds)) otherSpeed.speed.y += 200;
					}
				};
				newEntity.add(light);
				newEntity.add(interactive);
				return newEntity;
			}
		};
		creationManager.entityDescriptors.put("placeholderJumpBoost", jumpBoost);
	}

	private void loadAssets() {
		String assets = Gdx.files.internal("assets.txt").readString();
		Pattern pattern = Pattern.compile("\\[NormalTexture\\]\\n([^\\[]*)\\n?\\[RegularTexture\\]\n([^\\[]*)?\\n?");
		Matcher matcher = pattern.matcher(assets);
		if(!matcher.find()) Gdx.app.debug("Asset Loading", "No matches.");

		String normalTextures = matcher.group(1);
		String regularTextures = matcher.group(2);
		if(normalTextures.length() != 0)
			for(String s : normalTextures.split("\n")){
				assetManager.load("textures/" + s, Texture.class);
				assetManager.load("normals/" + s, Texture.class);
			}
		if(regularTextures.length() != 0)
			for(String s : regularTextures.split("\n")){
				assetManager.load("textures/" + s, Texture.class);
			}

		while(!assetManager.update());
	}

	@Override
	public void render () {
		Gdx.app.setLogLevel(debug && debugOutput ? Gdx.app.LOG_DEBUG : Gdx.app.LOG_INFO);
		/*
		 * Update the engine using the delta time
		 */
		pooledEngine.update(Gdx.graphics.getDeltaTime());
		Gdx.app.debug("FPS", Gdx.graphics.getFramesPerSecond() + " Frames Per Second");
	}


	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		orthoCam.setToOrtho(false, Constants.CAMERA_WIDTH, Math.round(Constants.CAMERA_WIDTH * height / width));
	}
}
