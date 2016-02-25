package com.danilafe.cave;

import java.util.Iterator;
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
import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.animation.AnimationParameter;
import com.danilafe.cave.creation.CreationManager;
import com.danilafe.cave.creation.EntityDescriptor;
import com.danilafe.cave.ecs.components.CAcceleration;
import com.danilafe.cave.ecs.components.CAnchor;
import com.danilafe.cave.ecs.components.CAnimation;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CCameraShake;
import com.danilafe.cave.ecs.components.CCameraView;
import com.danilafe.cave.ecs.components.CDamageCause;
import com.danilafe.cave.ecs.components.CDamageable;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CDisappearing;
import com.danilafe.cave.ecs.components.CFacing;
import com.danilafe.cave.ecs.components.CFacing.Direction;
import com.danilafe.cave.ecs.components.CFollow;
import com.danilafe.cave.ecs.components.CFrictionCause;
import com.danilafe.cave.ecs.components.CFrictionObject;
import com.danilafe.cave.ecs.components.CGravity;
import com.danilafe.cave.ecs.components.CHealth;
import com.danilafe.cave.ecs.components.CInteractionCause;
import com.danilafe.cave.ecs.components.CInteractive;
import com.danilafe.cave.ecs.components.CItem;
import com.danilafe.cave.ecs.components.CItemContainer;
import com.danilafe.cave.ecs.components.CLight;
import com.danilafe.cave.ecs.components.CMarked;
import com.danilafe.cave.ecs.components.CNormalObject;
import com.danilafe.cave.ecs.components.CNormalObstacle;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.components.CSpeedDamage;
import com.danilafe.cave.ecs.components.CStepper;
import com.danilafe.cave.ecs.components.CUnloading;
import com.danilafe.cave.ecs.components.CWeapon;
import com.danilafe.cave.ecs.systems.AccelerationSystem;
import com.danilafe.cave.ecs.systems.BoundsSystem;
import com.danilafe.cave.ecs.systems.CameraSystem;
import com.danilafe.cave.ecs.systems.DamageSystem;
import com.danilafe.cave.ecs.systems.DebugRenderSystem;
import com.danilafe.cave.ecs.systems.DisappearingSystem;
import com.danilafe.cave.ecs.systems.EmitterSystem;
import com.danilafe.cave.ecs.systems.FollowingSystem;
import com.danilafe.cave.ecs.systems.FrictionSystem;
import com.danilafe.cave.ecs.systems.GravitySystem;
import com.danilafe.cave.ecs.systems.InteractionSystem;
import com.danilafe.cave.ecs.systems.ItemSystem;
import com.danilafe.cave.ecs.systems.LightSystem;
import com.danilafe.cave.ecs.systems.NormalSystem;
import com.danilafe.cave.ecs.systems.PositionSystem;
import com.danilafe.cave.ecs.systems.RenderSystem;
import com.danilafe.cave.ecs.systems.SelectableElementSystem;
import com.danilafe.cave.ecs.systems.SpeedDamageSystem;
import com.danilafe.cave.ecs.systems.StepperSystem;
import com.danilafe.cave.ecs.systems.TileSystem;
import com.danilafe.cave.ecs.systems.WeaponSystem;
import com.danilafe.cave.item.ItemContainer;
import com.danilafe.cave.item.ItemParameter;
import com.danilafe.cave.runnable.ECSRunnable;
import com.danilafe.cave.tile.ChunkAnchor;
import com.danilafe.cave.tile.MapManager;
import com.danilafe.cave.tile.Tile;
import com.danilafe.cave.tile.TileAnimation;
import com.danilafe.cave.tile.TileParameter;

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
	 * System to handle items.
	 */
	public ItemSystem itemSystem;
	/**
	 * Tile system used to update anchors and tiles.
	 */
	public TileSystem tileSystem;
	/**
	 * Disappearing System handles disappearances
	 */
	public DisappearingSystem disappearingSystem;
	/**
	 * Manages particle emitters
	 */
	public EmitterSystem emitterSystem;
	/**
	 * Fast speed change --> damage
	 */
	public SpeedDamageSystem speedDamageSystem;
	/**
	 * Damages things.
	 */
	public DamageSystem damageSystem;
	/**
	 * Makes weapons do things!
	 */
	public WeaponSystem weaponSystem;
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
	 * Whether the collision boxes should be rendered (when debugging)
	 */
	public boolean collisionBoxes = false;
	/**
	 * Whether the debug output should be allowed (when debugging)
	 */
	public boolean debugOutput = false;
	/**
	 * Whether the engine should not be updated.
	 */
	public boolean executionStopped = false;
	/**
	 * The map manger used to load / unload chunks
	 */
	public MapManager mapManager = new MapManager();

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
		itemSystem = new ItemSystem();
		tileSystem = new TileSystem();
		disappearingSystem = new DisappearingSystem();
		emitterSystem = new EmitterSystem();
		speedDamageSystem = new SpeedDamageSystem();
		damageSystem = new DamageSystem();
		weaponSystem = new WeaponSystem();

		orthoCam = new OrthographicCamera(Constants.CAMERA_WIDTH, Constants.CAMERA_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

		pooledEngine.addSystem(gravitySystem);
		pooledEngine.addSystem(accelerationSystem);
		pooledEngine.addSystem(normalSystem);
		pooledEngine.addSystem(frictionSystem);
		pooledEngine.addSystem(positionSystem);
		pooledEngine.addSystem(cameraSystem);
		pooledEngine.addSystem(followingSystem);
		pooledEngine.addSystem(boundsSystem);
		pooledEngine.addSystem(lightSystem);
		pooledEngine.addSystem(renderSystem);
		pooledEngine.addSystem(debugRenderSystem);
		pooledEngine.addSystem(interactionSystem);
		pooledEngine.addSystem(selectableElementSytem);
		pooledEngine.addSystem(itemSystem);
		pooledEngine.addSystem(tileSystem);
		pooledEngine.addSystem(disappearingSystem);
		pooledEngine.addSystem(emitterSystem);
		pooledEngine.addSystem(stepperSystem);
		pooledEngine.addSystem(damageSystem);
		pooledEngine.addSystem(speedDamageSystem);
		pooledEngine.addSystem(weaponSystem);

		assetManager = new AssetManager();
		creationManager = new CreationManager();

		loadAssets();
		loadCreation();

		TileParameter newTileParam = creationManager.tileParameters.get("placeholderCavetilesParameter");
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderPlayer").create(50, 50));
		for(int i = 0; i < 10; i ++){
			Tile newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, Constants.TILE_SIZE * i, 0);
			newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, Constants.TILE_SIZE * i, 8);
			newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, Constants.TILE_SIZE * i, 16);
			newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, Constants.TILE_SIZE * i, 24);
			newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, 0, Constants.TILE_SIZE * (i));
			newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, 8, Constants.TILE_SIZE * (i));
			newTile = Tile.create(newTileParam, (int)(Math.random() * 3));
			mapManager.setTile(newTile, 72, Constants.TILE_SIZE * (i));
		}
		mapManager.setTile(Tile.create(newTileParam, (int)(Math.random() * 3)), 8, 16);

		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderJumpBoost").create(64, 24));

		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderCrystal").create(16, 32));
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderChest").create(72, 80));
		pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderJumpBoost").create(72, 72));

		pooledEngine.addEntity(creationManager.entityDescriptors.get("battleBox").create(32, 75));

		for (int i = 0; i < 16; i ++){
			pooledEngine.addEntity(creationManager.entityDescriptors.get("placeholderLightball").create((float) (Math.random() * 160), (float)(Math.random() * 160)));
		}

		if(Constants.DEBUG){
			pooledEngine.addEntity(creationManager.entityDescriptors.get("debugger").create(0, 0));
		}

	}

	private void loadCreation() {
		/*
		 * Animation parameters
		 */
		creationManager.animationParams.put("caveTiles", AnimationParameter.create("cavetiles.png", true, 8, 8, 0));
		creationManager.animationParams.put("battleBox", AnimationParameter.create("battlebox.png", true, 8, 8, 0));
		creationManager.animationParams.put("crystals", AnimationParameter.create("crystals.png", true, 8, 8, 0));
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
				speed.speed.set(0, 0);
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
							mySpeed.speed.y += 150;
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
				CAnchor anchor = pooledEngine.createComponent(CAnchor.class);
				anchor.anchor = new ChunkAnchor();
				anchor.anchor.range = 2;
				CCameraShake shake = pooledEngine.createComponent(CCameraShake.class);
				CDamageable damageable = pooledEngine.createComponent(CDamageable.class);
				damageable.team = "player";
				damageable.onDamage = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						Utils.shake(me.getComponent(CCameraShake.class), 0, 1F / 25, 10, 1, .7F);
					}
				};
				damageable.maxDelay = 1F / 4;
				damageable.knockbackMultiplier = 1;
				CHealth health = pooledEngine.createComponent(CHealth.class);
				health.health = 10;
				health.maxHealth = 10;
				CSpeedDamage speedDamage = pooledEngine.createComponent(CSpeedDamage.class);
				speedDamage.maxSpeed = 350;
				speedDamage.damagePerUnit = 10F / 150;
				CAnimation animation = pooledEngine.createComponent(CAnimation.class);
				Animation an = new Animation();
				an.animationParameter = creationManager.animationParams.get("caveTiles");
				animation.animationQueue.add(an);
				CFacing facing = pooledEngine.createComponent(CFacing.class);
				entity.add(facing);
				entity.add(animation);
				entity.add(speedDamage);
				entity.add(health);
				entity.add(damageable);
				entity.add(shake);
				entity.add(anchor);
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
				CAnimation animation = pooledEngine.createComponent(CAnimation.class);
				entity.add(animation);
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
				light.light.set(x, y, 25, .1F, .3F, .3F);
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
						for(Entity e : pooledEngine.getEntitiesFor(Family.all(CPosition.class, CSpeed.class, CBounds.class).exclude(CDisabled.class).get())){
							if(Family.all(CMarked.class).exclude(CDisabled.class).get().matches(e) && e.getComponent(CMarked.class).marks.get("floater").booleanValue()) continue;
							CBounds otherBounds = e.getComponent(CBounds.class);
							CSpeed otherSpeed = e.getComponent(CSpeed.class);
							if(myBounds.bounds.overlaps(otherBounds.bounds)){
								mySpeed.speed.add(otherSpeed.speed.cpy().scl(deltaTime).scl(.1F));
							}
						}
					}
				};
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.setSize(5, 5).setCenter(x, y);
				CAcceleration acceleration = pooledEngine.createComponent(CAcceleration.class);
				acceleration.scalarAcceleration.set(.8F, .8F);
				CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
				CGravity gravity = pooledEngine.createComponent(CGravity.class);
				gravity.gravity.set(0, -0.1F);
				CUnloading unloading = pooledEngine.createComponent(CUnloading.class);

				entity.add(unloading);
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
				newEntity.add(interactive);
				return newEntity;
			}
		};
		creationManager.entityDescriptors.put("placeholderJumpBoost", jumpBoost);
		EntityDescriptor chest = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CItemContainer container = pooledEngine.createComponent(CItemContainer.class);
				container.container = new ItemContainer();
				container.container.maxItems = 1;
				container.container.onOverflow = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						CItemContainer itemContainer = me.getComponent(CItemContainer.class);
						CPosition pos = me.getComponent(CPosition.class);
						Iterator<ItemParameter> overflowingItems = itemContainer.container.items.keySet().iterator();
						for(int i = 0; i < itemContainer.container.maxItems;  i++) overflowingItems.next();
						while(overflowingItems.hasNext()){
							Entity droppedItemEntity = creationManager.entityDescriptors.get("placeholderDroppedItem").create(pos.position.x, pos.position.y);
							CItem entityItem = droppedItemEntity.getComponent(CItem.class);
							entityItem.itemType = overflowingItems.next();
							entityItem.itemCount = itemContainer.container.items.get(entityItem.itemType);
							itemContainer.container.items.remove(entityItem.itemType);
							pooledEngine.addEntity(droppedItemEntity);
							Gdx.app.debug("Chest", "Overflow! Tossing " + entityItem.itemCount + " of " + entityItem.itemType.name);
						}
					}
				};
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.setSize(8, 8);
				CInteractive interactive = pooledEngine.createComponent(CInteractive.class);
				interactive.maxInteractDelay = .5F;
				interactive.interactKey = Keys.SHIFT_LEFT;
				interactive.onInteract = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						Utils.printContainerContents(me.getComponent(CItemContainer.class).container);
					}
				};
				entity.add(interactive);
				entity.add(bounds);
				entity.add(bounds);
				entity.add(container);
				entity.add(position);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderChest", chest);
		EntityDescriptor droppedItem = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CItem item = pooledEngine.createComponent(CItem.class);
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CSpeed speed = pooledEngine.createComponent(CSpeed.class);
				CFrictionObject fritionObject = pooledEngine.createComponent(CFrictionObject.class);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.setSize(4, 4);
				CGravity gravity = pooledEngine.createComponent(CGravity.class);
				gravity.gravity.set(0, Constants.DEFAULT_GRAVITY);
				CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
				CAnimation animation = pooledEngine.createComponent(CAnimation.class);
				CStepper stepper = pooledEngine.createComponent(CStepper.class);
				stepper.runnable = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						CItem myItem = me.getComponent(CItem.class);
						CAnimation myAnimation = me.getComponent(CAnimation.class);
						if(myAnimation.animationQueue.animationQueue.size() <= 0 && myItem.itemType.animation != null) {
							Animation newAnimation = new Animation();
							newAnimation.animationParameter = myItem.itemType.animation;
							myAnimation.animationQueue.add(newAnimation);
						}
					}
				};
				entity.add(animation);
				entity.add(normalObject);
				entity.add(gravity);
				entity.add(bounds);
				entity.add(fritionObject);
				entity.add(speed);
				entity.add(position);
				entity.add(item);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderDroppedItem", droppedItem);
		EntityDescriptor crystal = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.setSize(8, 8);
				CAnimation animation = pooledEngine.createComponent(CAnimation.class);
				Animation ani = new Animation();
				ani.animationParameter = creationManager.animationParams.get("crystals");
				ani.texIndex = (int) Math.floor(Math.random() * 2);
				animation.animationQueue.add(ani);
				CLight light = pooledEngine.createComponent(CLight.class);
				light.light.set(0, 0, 64, .7F, 1F, .7F, 0, 0, 0, 0);
				entity.add(light);
				entity.add(animation);
				entity.add(bounds);
				entity.add(position);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderCrystal", crystal);
		EntityDescriptor debugger = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity debuggerEntity = pooledEngine.createEntity();
				CStepper stepper = pooledEngine.createComponent(CStepper.class);
				stepper.runnable = new ECSRunnable() {
					@Override
					public void update(Entity me, float deltaTime) {
						if(Gdx.input.isKeyPressed(Keys.F1)){
							if(Gdx.input.isKeyJustPressed(Keys.B)){
								collisionBoxes = !collisionBoxes;
							}
							if(Gdx.input.isKeyJustPressed(Keys.O)){
								debugOutput = !debugOutput;
							}
							if(Gdx.input.isKeyJustPressed(Keys.P)){
								executionStopped = !executionStopped;
							}
							if(Gdx.input.isKeyJustPressed(Keys.S)){
								for(Entity e : pooledEngine.getEntitiesFor(Family.all(CCameraShake.class).exclude(CDisabled.class).get())){
									CCameraShake shake = e.getComponent(CCameraShake.class);
									Utils.shake(shake, .01F, .025F, 10, 1, .7F);
								}
							}
						}
					}
				};
				debuggerEntity.add(stepper);
				return debuggerEntity;
			}
		};
		creationManager.entityDescriptors.put("debugger", debugger);
		EntityDescriptor battleBox = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();

				CNormalObject normalObject = pooledEngine.createComponent(CNormalObject.class);
				CGravity gravity = pooledEngine.createComponent(CGravity.class);
				gravity.gravity.set(0, Constants.DEFAULT_GRAVITY);
				CSpeed speed = pooledEngine.createComponent(CSpeed.class);
				CPosition position = pooledEngine.createComponent(CPosition.class);
				position.position.set(x, y);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				bounds.bounds.setSize(8, 8).setCenter(x, y);
				CDamageCause damageCause = pooledEngine.createComponent(CDamageCause.class);
				damageCause.damage = 0;
				damageCause.additionalKnockback.set(0, 100);
				damageCause.knockback = 100;
				damageCause.maxDelay = 0;
				damageCause.teams.add("player");
				CDamageable damageable = pooledEngine.createComponent(CDamageable.class);
				damageable.damageMutliplier = 0;
				damageable.team = "environment";
				CUnloading unloading = pooledEngine.createComponent(CUnloading.class);
				CHealth health = pooledEngine.createComponent(CHealth.class);
				health.maxHealth = 10;
				health.health = 10;
				CFrictionObject frictionObject = pooledEngine.createComponent(CFrictionObject.class);
				CAnimation animation = pooledEngine.createComponent(CAnimation.class);
				Animation an = new Animation();
				an.animationParameter = creationManager.animationParams.get("battleBox");
				animation.animationQueue.add(an);

				entity.add(animation);
				entity.add(frictionObject);
				entity.add(health);
				entity.add(unloading);
				entity.add(damageable);
				entity.add(damageCause);
				entity.add(bounds);
				entity.add(position);
				entity.add(speed);
				entity.add(gravity);
				entity.add(normalObject);

				return entity;
			}
		};
		creationManager.entityDescriptors.put("battleBox", battleBox);
		EntityDescriptor weaponEntityDescritor = new EntityDescriptor() {
			@Override
			public Entity create(float x, float y) {
				Entity entity = pooledEngine.createEntity();
				CDamageCause damageCause = pooledEngine.createComponent(CDamageCause.class);
				CFollow follow = pooledEngine.createComponent(CFollow.class);
				CBounds bounds = pooledEngine.createComponent(CBounds.class);
				CWeapon weapon = pooledEngine.createComponent(CWeapon.class);
				CDisappearing disappearing = pooledEngine.createComponent(CDisappearing.class);
				CPosition position = pooledEngine.createComponent(CPosition.class);

				entity.add(position);
				entity.add(disappearing);
				entity.add(weapon);
				entity.add(bounds);
				entity.add(follow);
				entity.add(damageCause);
				return entity;
			}
		};
		creationManager.entityDescriptors.put("placeholderWeaponBase", weaponEntityDescritor);
		/*
		 * Tile animations
		 */
		TileAnimation cavetilesAnimation = TileAnimation.create("cavetiles.png", 8, 8, 0, true);
		creationManager.tileAnimations.put("placeholderCavetilesAnimation", cavetilesAnimation);
		/*
		 * Tile parameters
		 */
		TileParameter cavetilesParameter = TileParameter.create(cavetilesAnimation, placeholderWall);
		creationManager.tileParameters.put("placeholderCavetilesParameter", cavetilesParameter);

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
		mapManager.update();

		Gdx.app.setLogLevel(debugOutput ? Gdx.app.LOG_DEBUG : Gdx.app.LOG_INFO);
		/*
		 * Update the engine using the delta time
		 */
		if(!executionStopped)
			pooledEngine.update(Gdx.graphics.getDeltaTime());
		Gdx.app.debug("FPS", Gdx.graphics.getFramesPerSecond() + " Frames Per Second");
	}


	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		orthoCam.setToOrtho(false, Constants.CAMERA_WIDTH, Math.round(Constants.CAMERA_WIDTH * height / width));
	}
}
