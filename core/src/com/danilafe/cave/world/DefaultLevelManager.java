package com.danilafe.cave.world;

import com.danilafe.cave.CaveGame;
import com.danilafe.cave.creation.EntityDescriptor;
import com.danilafe.cave.tile.Tile;
import com.danilafe.cave.tile.TileParameter;

/**
 * Default implementation of a LevelManager
 * @author vanilla
 *
 */
public class DefaultLevelManager extends LevelManager {

	@Override
	public LevelData save() {
		return null;
	}

	@Override
	public void load(LevelData levelData) {
		for(String tileKey : levelData.tiles.keySet()) {
			TileParameter parameter = CaveGame.instance.creationManager.tileParameters.get(tileKey);
			for(SavedTile tile : levelData.tiles.get(tileKey)) {
				CaveGame.instance.mapManager.setTile(
						Tile.create(parameter, tile.tileVariation),
						(int) tile.position.x, (int) tile.position.y);
			}
		}
		for(String entityKey : levelData.entities.keySet()) {
			EntityDescriptor entityDescriptor = CaveGame.instance.creationManager.entityDescriptors.get(entityKey);
			for(SavedEntity entity : levelData.entities.get(entityKey)) {
				CaveGame.instance.pooledEngine.addEntity(entityDescriptor.create(entity.position.x, entity.position.y));
			}
		}
	}


}
