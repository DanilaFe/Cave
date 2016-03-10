package com.danilafe.cave.world;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

/**
 * LevelData class to hold entities and tiles present in a level.
 * @author vanilla
 *
 */
public class LevelData {

	/**
	 * The tiles stored in this level.
	 */
	public HashMap<String, ArrayList<SavedTile>> tiles = new HashMap<String, ArrayList<SavedTile>>();
	/**
	 * The entities stored in this level
	 */
	public HashMap<String, ArrayList<SavedEntity>> entities = new HashMap<String, ArrayList<SavedEntity>>();

	/**
	 * Adds the given tile to the level
	 * @param tileName the type of the tile
	 * @param position the position of the tile
	 */
	public void addTile(String tileName, Vector2 position, int tileVariation) {
		if(!tiles.containsKey(tileName)) tiles.put(tileName, new ArrayList<SavedTile>());
		SavedTile savedTile = new SavedTile();
		savedTile.position = position.cpy();
		savedTile.tileVariation = tileVariation;
		tiles.get(tileName).add(savedTile);
	}

	/**
	 * Convenience method to create tile
	 * @param tileName the name of the tile
	 * @param posX the x-position of the tile
	 * @param posY the y-position of the tile
	 * @param variation the variation of the tile
	 */
	public void addTile(String tileName, float posX, float posY, int variation) { addTile(tileName, new Vector2(posX, posY), variation); }

	/**
	 * Adds the given entity to the level
	 * @param entityName the type of the entity
	 * @param position the position of the entity
	 */
	public void addEntity(String entityName, Vector2 position){
		if(!entities.containsKey(entityName)) entities.put(entityName, new ArrayList<SavedEntity>());
		SavedEntity savedEntity = new SavedEntity();
		savedEntity.position = position.cpy();
		entities.get(entityName).add(savedEntity);
	}

	/**
	 * Convenience method to create entity
	 * @param entityName the name of the entity
	 * @param posX the x-position of the entity
	 * @param posY the y-position of the entity
	 */
	public void addEntity(String entityName, float posX, float posY) { addEntity(entityName, new Vector2(posX, posY)); }

}
