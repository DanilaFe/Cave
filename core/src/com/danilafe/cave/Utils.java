package com.danilafe.cave;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.attacks.Weapon;
import com.danilafe.cave.attacks.WeaponDecriptor;
import com.danilafe.cave.creation.EntityDescriptor;
import com.danilafe.cave.ecs.components.CCameraShake;
import com.danilafe.cave.ecs.components.CDamageable;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CDisappearing;
import com.danilafe.cave.ecs.components.CFollow;
import com.danilafe.cave.ecs.components.CGroupElement;
import com.danilafe.cave.ecs.components.CHealth;
import com.danilafe.cave.ecs.components.CInteractive;
import com.danilafe.cave.ecs.components.CTile;
import com.danilafe.cave.ecs.components.CWeapon;
import com.danilafe.cave.ecs.components.CWeaponWielding;
import com.danilafe.cave.gui.GUIElement;
import com.danilafe.cave.gui.GUITexture;
import com.danilafe.cave.health.DamageData;
import com.danilafe.cave.item.ItemContainer;
import com.danilafe.cave.item.ItemParameter;
import com.danilafe.cave.player.KeyBinds;
import com.danilafe.cave.player.PlayerStatistic;
import com.danilafe.cave.tile.Tile;
import com.danilafe.cave.world.LevelData;
import com.danilafe.cave.world.WorldLoadParam;

/**
 * Utils class to hold utility methods.
 *
 * @author vanilla
 *
 */
public class Utils {

	/**
	 * Checks whether the given edge of the rectangle touches the edge of
	 * another rectangle.
	 *
	 * @param edgeId
	 *            the ID of the edge - 0 is up, 1 is right, 2 is down and 3 is
	 *            left.
	 * @param primary
	 *            the rectangle whose side is being checked
	 * @param secondary
	 *            the rectangle checked against
	 * @return true if the given side of the rectangle touches a side of the
	 *         other rectangle.
	 */
	public static boolean checkEdgeContact(int edgeId, Rectangle primary, Rectangle secondary) {
		float bottomY = secondary.y;
		float topY = bottomY + secondary.height;
		float bottomX = secondary.x;
		float topX = bottomX + secondary.width;
		if (edgeId == 0 && (bottomY == primary.y + primary.height) && (primary.x + primary.width > bottomX)
				&& (primary.x < topX))
			return true;
		else if (edgeId == 1 && (bottomX == primary.x + primary.width) && (primary.y + primary.height > bottomY)
				&& (primary.y < topY))
			return true;
		else if (edgeId == 2 && (topY == primary.y) && (primary.x + primary.width > bottomX) && (primary.x < topX))
			return true;
		else if (edgeId == 3 && (topX == primary.x) && (primary.y + primary.height > bottomY) && (primary.y < topY))
			return true;
		return false;
	}

	/**
	 * Checks whether the two rectangles' edges are touching
	 * @param primary the rectangle whose edges are checked
	 * @param secondary the angle against which is check against
	 * @return true if edges are in contact
	 */
	public static boolean checkEdgeContact(Rectangle primary, Rectangle secondary) {
		for (int i = 0; i < 4; i++)
			if (checkEdgeContact(i, primary, secondary))
				return true;
		return false;
	}

	/**
	 * Shader function. This loads the GLSL shaders for the game.
	 *
	 * @param shaderName
	 *            the name of the shader to load.
	 * @return the loaded and compiled shader program, or null of compilation
	 *         failed.
	 */
	public static ShaderProgram loadShaders(String shaderName) {
		if (shaderName == null || shaderName.equals("") || !Gdx.files.internal("shaders/" + shaderName).exists())
			return null;
		ShaderProgram.pedantic = false;
		ShaderProgram newProgram = new ShaderProgram(
				Gdx.files.internal("shaders/" + shaderName + "/vertex.glsl").readString(),
				Gdx.files.internal("shaders/" + shaderName + "/fragment.glsl").readString());
		if (!newProgram.isCompiled()) {
			Gdx.app.error("Shader Loading", newProgram.getLog());
			return null;
		}
		return newProgram;
	}

	/**
	 * Prints the items in this container. (For debug purposes)
	 *
	 * @param container
	 *            the container whose contents to print.
	 */
	public static void printContainerContents(ItemContainer container) {
		for (ItemParameter key : container.items.keySet()) {
			Gdx.app.debug("Chest Contents", key.name + " | " + key.description + " | " + container.items.get(key));
		}
	}

	/**
	 * Safely remove entity from game (attempts to find all references to this
	 * entity)
	 *
	 * @param e
	 *            the entity to remove
	 */
	public static void removeEntity(Entity e) {
		for(Entity entity : CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CFollow.class).get())) {
			CFollow followC;
			if((followC = entity.getComponent(CFollow.class)).following == e) followC.following = null;
		}
		for(Entity entity : CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CInteractive.class).get())) {
			CInteractive interactiveC;
			if((interactiveC = entity.getComponent(CInteractive.class)).lastInteraction == e) interactiveC.lastInteraction = null;
			if(interactiveC.currentInteraction == e) interactiveC.currentInteraction = null;
		}
		for(Entity entity : CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CGroupElement.class).get())) {
			CGroupElement groupElementC;
			if((groupElementC = entity.getComponent(CGroupElement.class)).entityList.contains(e)) groupElementC.entityList.remove(e);
		}
		for(Entity entity : CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CWeaponWielding.class).get())){
			CWeaponWielding weaponWieldingC;
			if((weaponWieldingC = entity.getComponent(CWeaponWielding.class)).weaponEntity == e) weaponWieldingC.weaponEntity = null;
		}
		CaveGame.instance.pooledEngine.removeEntity(e);
	}

	/**
	 * Creates an entity from the tile.
	 *
	 * @param toCreate
	 *            the tile to create an entity from
	 * @return the created entity
	 */
	public static Entity createEntityFromTile(Tile toCreate) {
		Entity createdEntiy = toCreate.tileParameter.entityType
				.create(toCreate.myChunk.position.x + toCreate.position.x,
						toCreate.myChunk.position.y + toCreate.position.y);
		CTile tile = CaveGame.instance.pooledEngine.createComponent(CTile.class);
		tile.myTile = toCreate;
		createdEntiy.add(tile);
		return createdEntiy;
	}

	/**
	 * Deletes the first entity associated with this tile.
	 *
	 * @param toDestroy
	 *            the tile whose entity to delete
	 */
	public static void destroyEntityFromTile(Tile toDestroy) {
		for (Entity e : CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CTile.class).exclude(CDisabled.class).get())) {
			if (e.getComponent(CTile.class).myTile == toDestroy)
				CaveGame.instance.pooledEngine.removeEntity(e);
		}
	}

	/**
	 * Updates the tile's animation and orientation based on its surroundings
	 * @param tile the tile to update
	 */
	public static void updateTileNeighbors(Tile tile) {
		Vector2 worldPos = tile.position.cpy().add(tile.myChunk.position);

		if(!tile.tileParameter.animation.isDifferent){
			tile.rotation = 0;
			tile.currentAnimation = tile.tileParameter.animation.noNeighbors[tile.tileVariation];
			return;
		}

		boolean[] neighbors = new boolean[4];
		neighbors[0] = CaveGame.instance.mapManager.getTile((int) worldPos.x,
				(int) worldPos.y + Constants.TILE_SIZE) != null;
		neighbors[2] = CaveGame.instance.mapManager.getTile((int) worldPos.x,
				(int) worldPos.y - Constants.TILE_SIZE) != null;
		neighbors[1] = CaveGame.instance.mapManager.getTile((int) worldPos.x + Constants.TILE_SIZE,
				(int) worldPos.y) != null;
		neighbors[3] = CaveGame.instance.mapManager.getTile((int) worldPos.x - Constants.TILE_SIZE,
				(int) worldPos.y) != null;
		int neighborCount = 0;
		for (int i = 0; i < 4; i++)
			if (neighbors[i])
				neighborCount++;

		tile.animationChange = true;

		if (neighborCount == 0) {
			if (tile.tileParameter.animation != null && tile.tileParameter.animation.noNeighbors != null) {
				tile.currentAnimation = tile.tileParameter.animation.noNeighbors[tile.tileVariation];
			}
		} else if (neighborCount > 0 && neighborCount < 4) {
			for (int i = 0; i < 4; i++) {
				if (neighborCount == 1) {
					if (neighbors[i]) {
						tile.rotation = 90 * i;
						if (tile.tileParameter.animation != null && tile.tileParameter.animation.oneNeighbor != null)
							tile.currentAnimation = tile.tileParameter.animation.oneNeighbor[tile.tileVariation];
						break;
					}
				} else if (neighborCount == 2) {
					if (!neighbors[i] && !neighbors[(i + 1) % 4]) {
						tile.rotation = 90 * i;
						if (tile.tileParameter.animation != null && tile.tileParameter.animation.corner != null)
							tile.currentAnimation = tile.tileParameter.animation.corner[tile.tileVariation];
						break;
					} else if (!neighbors[i] && !neighbors[(i + 2) % 4]) {
						tile.rotation = 90 * i;
						if (tile.tileParameter.animation != null && tile.tileParameter.animation.twoNeighbors != null)
							tile.currentAnimation = tile.tileParameter.animation.twoNeighbors[tile.tileVariation];
						break;
					}
				} else if (neighborCount == 3 && !neighbors[i]) {
					tile.rotation = 90 * i;
					if (tile.tileParameter.animation != null && tile.tileParameter.animation.threeNeighbors != null)
						tile.currentAnimation = tile.tileParameter.animation.threeNeighbors[tile.tileVariation];
					break;
				}
			}
		} else if (neighborCount == 4) {
			if (tile.tileParameter.animation != null && tile.tileParameter.animation.fourNeighbor != null)
				tile.currentAnimation = tile.tileParameter.animation.fourNeighbor[tile.tileVariation];
		}

	}

	/**
	 * Updates all the tiles nearby the given location position
	 * @param center the position to update around
	 */
	public static void updateNearby(Vector2 center){
		Tile placeholderTile;
		if((placeholderTile = CaveGame.instance.mapManager.getTile((int) center.x,(int) center.y)) != null) updateTileNeighbors(placeholderTile);
		if((placeholderTile = CaveGame.instance.mapManager.getTile((int) center.x + Constants.TILE_SIZE,(int) center.y)) != null) updateTileNeighbors(placeholderTile);
		if((placeholderTile = CaveGame.instance.mapManager.getTile((int) center.x - Constants.TILE_SIZE,(int) center.y)) != null) updateTileNeighbors(placeholderTile);
		if((placeholderTile = CaveGame.instance.mapManager.getTile((int) center.x,(int) center.y + Constants.TILE_SIZE)) != null) updateTileNeighbors(placeholderTile);
		if((placeholderTile = CaveGame.instance.mapManager.getTile((int) center.x,(int) center.y - Constants.TILE_SIZE)) != null) updateTileNeighbors(placeholderTile);
	}

	/**
	 * Shakes the camera.
	 * @param shake The camerashake component that this is being applied to
	 * @param delay delay between switching shake directions
	 * @param maxDelay maximum delay between switching shake directions
	 * @param distance the distances from the player the shake should go
	 * @param resetThreshold how small the distance has to become before it's set to 0
	 * @param distanceDamping by how much distance is reduced
	 */
	public static void shake(CCameraShake shake, float delay, float maxDelay, float distance, float resetThreshold, float distanceDamping){
		shake.delay = delay;
		shake.maxDelay = maxDelay;
		shake.distance = distance;
		shake.resetThreshold = resetThreshold;
		shake.distanceDamping = distanceDamping;
	}

	/**
	 * Deals damage to this entity. Does not provide a cause.
	 * @param damageTo the entity to deal damage to
	 * @param damage the damge to deal
	 * @param useMultiplier whether to use the damageMultiplier value of CDamageable
	 * @param cooldown whether to set the damageable to cooldown mode
	 * @param dealIfCooldown whether to damage if the damageable is cooling down
	 * @param deltaTime the delta time
	 */
	public static void dealDamage(Entity damageTo, float damage, boolean useMultiplier, boolean cooldown, boolean dealIfCooldown, float deltaTime){
		CDamageable damageable = damageTo.getComponent(CDamageable.class);
		CHealth health = damageTo.getComponent(CHealth.class);


		if(useMultiplier) damage *= damageable.damageMutliplier;
		if(!dealIfCooldown && damageable.delay != 0) return;
		if(cooldown) damageable.delay = damageable.maxDelay;

		DamageData damageData = new DamageData();
		damageData.damage = damage;
		damageData.damageTo = damageTo;
		damageable.currentDamage = damageData;

		damageable.onDamage.update(damageTo, deltaTime);

		health.health -= damage;
		if(health.health <= 0 && health.onNoHealth != null) health.onNoHealth.update(damageTo, deltaTime);
	}

	/**
	 * Creates a new weapon entity from the given descriptor. <br>
	 * @param damageSource the entity who created this weapon
	 * @param createFrom the weapon descriptor
	 * @return the created weapon entity
	 */
	public static Entity createWeaponEntity(Entity damageSource, WeaponDecriptor createFrom) {
		Entity newEntity = createFrom.entityDescriptor.create(0, 0);
		CFollow follow = newEntity.getComponent(CFollow.class);
		follow.following = damageSource;
		CDisappearing disappearing = newEntity.getComponent(CDisappearing.class);
		disappearing.remaingTime = createFrom.weaponAttacks.duration;
		CWeapon weapon = newEntity.getComponent(CWeapon.class);
		Weapon createdWeapon = Weapon.create(createFrom);
		weapon.weapon = createdWeapon;

		return newEntity;
	}

	/**
	 * Reads a vector from the given input stream
	 * @param readFrom the stream to read from
	 * @return the produced vector
	 * @throws IOException
	 */
	public static Vector2 readVector(InputStream readFrom) throws IOException {
		DataInputStream inputStream = new DataInputStream(readFrom);
		return new Vector2(inputStream.readFloat(), inputStream.readFloat());
	}

	/**
	 * Writes a vector to the given output stream
	 * @param writeTo the stream to rite to
	 * @param vec the vector to write
	 * @throws IOException
	 */
	public static void writeVector(OutputStream writeTo, Vector2 vec) throws IOException {
		DataOutputStream outputStream = new DataOutputStream(writeTo);
		outputStream.writeFloat(vec.x);
		outputStream.writeFloat(vec.y);
	}

	/**
	 * Reads an integer from the given stream
	 * @param readFrom the stream to read from
	 * @return the produced int
	 * @throws IOException
	 */
	public static int readInt(InputStream readFrom) throws IOException {
		DataInputStream inputStream = new DataInputStream(readFrom);
		return inputStream.readInt();
	}

	/**
	 * Writes an integer to the given stream
	 * @param writeTo the stream to write to
	 * @param i the integer to write
	 * @throws IOException
	 */
	public static void writeInt(OutputStream writeTo, int i) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(writeTo);
		dataOutputStream.writeInt(i);
	}

	/**
	 * Reads a float from the given input stream
	 * @param readFrom the stream to read from
	 * @return the produced float
	 * @throws IOException
	 */
	public static float readFloat(InputStream readFrom) throws IOException {
		DataInputStream inputStream = new DataInputStream(readFrom);
		return inputStream.readFloat();
	}

	/**
	 * Writes the given float to the given stream
	 * @param writeTo the stream to write to
	 * @param f the float to write
	 * @throws IOException
	 */
	public static void writeFloat(OutputStream writeTo, float f) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(writeTo);
		dataOutputStream.writeFloat(f);
	}

	/**
	 * Writes the given string to the given output stream
	 * @param writeTo the stream to write to
	 * @param s the string to write
	 * @throws IOException
	 */
	public static void writeString(OutputStream writeTo, String s) throws IOException {
		writeTo.write(s.length());
		writeTo.write(s.getBytes());
	}

	/**
	 * Reads a string from the given input stream
	 * @param readFrom the stream to read from
	 * @return the produced string
	 * @throws IOException
	 */
	public static String readString(InputStream readFrom) throws IOException {
		int length = readFrom.read();
		System.out.println("length: " + length);
		byte[] newBuffer = new byte[length];
		readFrom.read(newBuffer);

		return new String(newBuffer);
	}

	/**
	 * Disables the entity in the game world
	 * @param e the entity to disable
	 */
	public static void chunkEntity(Entity e) {
		CDisabled disabled = CaveGame.instance.pooledEngine.createComponent(CDisabled.class);
		disabled.reason = "chunked";
		e.add(disabled);
	}

	/**
	 * Renders the given GUI element onto sprite batch provided (using world coords)
	 * @param element the element to render
	 * @param renderTo the batch to render to
	 */
	public static void renderGUIElement(GUIElement element, SpriteBatch renderTo) {
		Vector2 renderVector = element.worldPos.cpy();
		renderVector.x = Math.round(renderVector.x);
		renderVector.y = Math.round(renderVector.y);
		renderGUIElement(element, renderTo, renderVector);
	}

	/**
	 * Renders this GUI element at the given position
	 * @param element the elemnt to render
	 * @param renderTo the sprite batch to render to
	 * @param renderVector the position vector (location of GUI element)
	 */
	public static void renderGUIElement(GUIElement element, SpriteBatch renderTo, Vector2 renderVector){
		GUITexture toUse = (element.selected) ? element.selectedTexture : element.guiTexture;
		if(toUse != null){
			for(int w = 1; w < element.width - 1; w++){
				renderTo.draw(toUse.textureRegion[2][1], renderVector.x + w * Constants.GUI_UNIT_SIZE, renderVector.y);
				renderTo.draw(toUse.textureRegion[0][1], renderVector.x + w * Constants.GUI_UNIT_SIZE, renderVector.y + (element.height - 1) * Constants.GUI_UNIT_SIZE);
				for(int h = 1; h < element.height -1; h++){
					renderTo.draw(toUse.textureRegion[1][1], renderVector.x + w * Constants.GUI_UNIT_SIZE, renderVector.y + h * Constants.GUI_UNIT_SIZE);
				}
			}

			for(int h = 1; h < element.height - 1; h++){
				renderTo.draw(toUse.textureRegion[1][0], renderVector.x, renderVector.y + h * Constants.GUI_UNIT_SIZE);
				renderTo.draw(toUse.textureRegion[1][2], renderVector.x + (element.width - 1) * Constants.GUI_UNIT_SIZE, renderVector.y + h * Constants.GUI_UNIT_SIZE);
			}

			renderTo.draw(toUse.textureRegion[2][0], renderVector.x, renderVector.y);
			renderTo.draw(toUse.textureRegion[2][2], renderVector.x + (element.width - 1) * Constants.GUI_UNIT_SIZE, renderVector.y);
			renderTo.draw(toUse.textureRegion[0][0], renderVector.x, renderVector.y + (element.height - 1) * Constants.GUI_UNIT_SIZE);
			renderTo.draw(toUse.textureRegion[0][2], renderVector.x + (element.width - 1) * Constants.GUI_UNIT_SIZE, renderVector.y + (element.height - 1) * Constants.GUI_UNIT_SIZE);
		}

		element.renderPosition.set(renderVector);
		if(element.onRender != null) element.onRender.render(renderTo, element);

		Vector2 topLeft = renderVector.cpy().add(0, element.height * Constants.GUI_UNIT_SIZE);
		for(GUIElement guiElement : element.children){
			renderGUIElement(guiElement, renderTo, topLeft.cpy()
					.add(guiElement.pos_x * Constants.GUI_UNIT_SIZE,
							-guiElement.pos_y * Constants.GUI_UNIT_SIZE -guiElement.height * Constants.GUI_UNIT_SIZE));
		}
	}

	/**
	 * Stores default key binding values into the keyBinds object
	 * @param keyBinds the keyBinds to write to
	 */
	public static void defaultKeyBinds(KeyBinds keyBinds){
		keyBinds.setKey("left", Keys.LEFT);
		keyBinds.setKey("right", Keys.RIGHT);
		keyBinds.setKey("jump", Keys.SPACE);
		keyBinds.setKey("melee", Keys.Z);
		keyBinds.setKey("magic", Keys.X);
		keyBinds.setKey("inv", Keys.Q);
	}

	/**
	 * Create a player file at the given location
	 * @param file the file to use
	 * @return the created player data
	 */
	public static PlayerStatistic createPlayerStat(FileHandle file){
		PlayerStatistic playerStatitic = new PlayerStatistic();
		playerStatitic.name = file.name();
		defaultKeyBinds(playerStatitic.keyBinds);
		try {
			playerStatitic.save(file.write(false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return playerStatitic;
	}

	/**
	 * Loads all the data used for initial world creation.
	 */
	public static void loadWorld(WorldLoadParam worldLoadParam){
		worldLoadParam.levelManager.load(worldLoadParam.levelData);
		CaveGame.instance.pooledEngine
		.addEntity(CaveGame.instance.creationManager.entityDescriptors.get("placeholderPlayer")
				.create(worldLoadParam.playerPosition.x, worldLoadParam.playerPosition.y));
	}

	/**
	 * Creates a new player from the given player statistic
	 * @param x the x-positon of the player
	 * @param y the y-position of the player
	 * @param playerStat the player statistic to use
	 */
	public static void createPlayer(float x, float y, PlayerStatistic playerStat){
		EntityDescriptor playerDesc = CaveGame.instance.creationManager.entityDescriptors.get("placeholderPlayer");
		CaveGame.instance.pooledEngine.addEntity(playerDesc.create(x, y));
	}

	/**
	 * Generates a world.
	 * @param x the width of the world
	 * @param y the height of the world
	 * @return the generated world's data
	 */
	public static LevelData generateWorld(float x, float y){
		LevelData newLevelData = new LevelData();

		float offset = (float) (Math.PI * Math.random() * 2);

		for(int i = 0; i < x; i+= Constants.TILE_SIZE){
			int numTiles = (int) Math.round((Math.sin(i) + 2) * 2) + 2;
			System.out.println(numTiles);
			for(int t = 0; t < numTiles; t++){
				newLevelData.addTile("placeholderCavetilesParameter", i, t * Constants.TILE_SIZE + offset, (int) (Math.random() * 3));
			}
			if(Math.random() < .15D) newLevelData.addEntity("placeholderCrystal", i, numTiles * Constants.TILE_SIZE);
		}

		for(int i = 0; i < 16; i++){
			newLevelData.addEntity("placeholderLightball", (float) Math.random() * x, (float) Math.random() * y);
		}

		return newLevelData;
	}

}
