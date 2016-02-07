package com.danilafe.cave.tile;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Constants;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CTile;
import com.danilafe.cave.ecs.components.CUnloading;

/**
 * Map manager used to manage in-game tiles, by loading / unloading them
 * @author vanilla
 *
 */
public class MapManager {

	/**
	 * The largest existing quad node
	 */
	public QuadNode mainNode = new QuadNode();
	/**
	 * List of anchors that this manager has. Anchors keep tiles around them loaded
	 */
	public ArrayList<ChunkAnchor> anchors = new ArrayList<ChunkAnchor>();
	/**
	 * Whether this chunk manager needs to be updated.
	 */
	public boolean needsUpdate = false;

	/**
	 * Create a new MapManager
	 */
	public MapManager() {
		mainNode.size = Constants.CHUNK_SIZE;
	}


	/**
	 * Returns the chunk at the given world position
	 * @param x the x-position of the chunk
	 * @param y the y-position of the chunk
	 * @return
	 */
	public Chunk getChunkAt(float x, float y){
		// Special Cases
		if(x % Constants.CHUNK_SIZE == 0) x += 1;
		if(y % Constants.CHUNK_SIZE == 0) y += 1;

		int chunkX = (int) Math.ceil(x / Constants.CHUNK_SIZE);
		int chunkY = (int) Math.ceil(y / Constants.CHUNK_SIZE);
		int biggetValue = (chunkX > chunkY) ? chunkX : chunkY;
		int requiredPower = (int) (Math.ceil(Math.log(biggetValue) / Math.log(2)));
		int requiredSize = (int) Math.pow(2, requiredPower) * Constants.CHUNK_SIZE;

		Gdx.app.debug("World Tree", "Required Size " + requiredSize);

		mainNode = increaseUntilSize(requiredSize, mainNode);
		return accessChunk(mainNode, x, y, x, y);
	}

	/**
	 * Recursively generates bigger parent nodes until the size requirement is met
	 * @param size the size requirement
	 * @param mainNode the current largest node
	 * @return the new largest node
	 */
	public QuadNode increaseUntilSize(int size, QuadNode mainNode){
		if(mainNode.size >= size) return mainNode;
		else {
			QuadNode newNode = new QuadNode();
			newNode.size = mainNode.size * 2;
			newNode.bl = mainNode;
			Gdx.app.debug("World Tree", "Created new node with size " + newNode.size);

			return increaseUntilSize(size, newNode);
		}
	}

	/**
	 * Recursively finds the chunk at the given position
	 * @param node the node to look through, should be the root node.
	 * @param relativeX used in recursion. should be equivalent to absolute x
	 * @param relativeY used in recursion. should be equivalent to absolute y
	 * @param absoluteX the x-position at which the chunk is being accessed
	 * @param absoluteY the y-position at which the chunk is being accessed
	 * @return the chunk at the given position.
	 */
	public Chunk accessChunk(QuadNode node, float relativeX, float relativeY, float absoluteX, float absoluteY){
		if(node.size == Constants.CHUNK_SIZE) {
			if(node.holdsChunk == null) {
				node.holdsChunk = new Chunk();
				node.holdsChunk.position.set((float) Math.floor(absoluteX / Constants.CHUNK_SIZE) * Constants.CHUNK_SIZE, (float) Math.floor(absoluteY / Constants.CHUNK_SIZE) * Constants.CHUNK_SIZE);
				Gdx.app.debug("World Tree", "Chunk not initialized. Creating new.");
			}
			return node.holdsChunk;
		}
		boolean secondX = relativeX >= node.size / 2;
		boolean secondY = relativeY >= node.size / 2;

		if(secondX) relativeX -= node.size / 2;
		if(secondY) relativeY -= node.size / 2;

		if(secondX && secondY) {
			if(node.tr == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent top-right node. Creating new.");
				node.tr = new QuadNode();
				node.tr.size = node.size / 2;
			}
			return accessChunk(node.tr, relativeX, relativeY, absoluteX, absoluteY);
		} else if(secondX){
			if(node.br == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent botoom-right node. Creating new.");
				node.br = new QuadNode();
				node.br.size = node.size / 2;
			}
			return accessChunk(node.br, relativeX, relativeY, absoluteX, absoluteY);
		} else if(secondY){
			if(node.tl == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent top-left node. Creating new.");
				node.tl = new QuadNode();
				node.tl.size = node.size / 2;
			}
			return accessChunk(node.tl, relativeX, relativeY, absoluteX, absoluteY);
		} else {
			if(node.bl == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent bottom-left node. Creating new.");
				node.bl = new QuadNode();
				node.bl.size = node.size / 2;
			}
			return accessChunk(node.bl, relativeX, relativeY, absoluteX, absoluteY);
		}
	}

	/**
	 * Sets the isLoaded value of all chunks in the given node, saving the previous value into the wasLoaded
	 * @param node the root node in which to find the chunks
	 * @param mark the mark to set the chunks to
	 */
	public void markAllChunks(QuadNode node, boolean mark){
		if(node.size == Constants.CHUNK_SIZE && node.holdsChunk != null) {
			node.holdsChunk.setLoaded(mark);
		}
		else {
			if(node.bl != null) markAllChunks(node.bl, mark);
			if(node.br != null) markAllChunks(node.br, mark);
			if(node.tl != null) markAllChunks(node.tl, mark);
			if(node.tr != null) markAllChunks(node.tr, mark);
		}
	}

	/**
	 * Sets all the anchored chunks's isLoaded to true, without backing up the value.
	 */
	public void markAnchored(){
		for(ChunkAnchor anchor : anchors){
			Chunk centerChunk =getChunkAt(anchor.position.x, anchor.position.y);
			for(int x = -anchor.range; x <= anchor.range; x++){
				for(int y = -anchor.range; y <= anchor.range; y++){
					float newX = (x * Constants.CHUNK_SIZE) + centerChunk.position.x;
					float newY = (y * Constants.CHUNK_SIZE) + centerChunk.position.y;
					if(newX < 0 || newY < 0) continue;
					Chunk chunk = getChunkAt(newX, newY);
					if(!chunk.isLoaded) chunk.isLoaded = (true);
				}
			}
		}
	}

	/**
	 * Loads the tiles in this chunk into the game world
	 * @param chunk the chunk to load
	 */
	public void loadChunk(Chunk chunk){
		Gdx.app.debug("World Tree", "Loading Chunk");
		for(int x = 0; x < Constants.CHUNK_SIZE / Constants.TILE_SIZE; x++){
			for(int y = 0; y < Constants.CHUNK_SIZE/ Constants.TILE_SIZE; y++){
				Tile toCreate = chunk.getTile(x, y);
				if(toCreate == null) continue;
				CaveGame.instance.pooledEngine.addEntity(Utils.createEntityFromTile(toCreate));
			}
		}
		ImmutableArray<Entity> unloadedEntities = CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CUnloading.class, CPosition.class, CDisabled.class).get());
		for(int i = 0; i < unloadedEntities.size(); i++){
			Entity e = unloadedEntities.get(i);
			CPosition entityPosition = e.getComponent(CPosition.class);
			float chunkX = (float) Math.floor(entityPosition.position.x / Constants.CHUNK_SIZE) * Constants.CHUNK_SIZE;
			float chunkY = (float) Math.floor(entityPosition.position.y / Constants.CHUNK_SIZE) * Constants.CHUNK_SIZE;

			if(e.getComponent(CDisabled.class).reason == "chunked" &&  chunkX == chunk.position.x && chunkY == chunk.position.y) {
				e.remove(CDisabled.class);

				i = -1;
				unloadedEntities = CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CUnloading.class, CPosition.class, CDisabled.class).get());
			}
		}
	}

	/**
	 * Removes the entities in the given chunk from the world
	 * @param chunk the chunk to unload
	 */
	public void unloadChunk(Chunk chunk){
		Gdx.app.debug("World Tree", "Unloading Chunk");
		ImmutableArray<Entity> tileEntities = CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CTile.class).exclude(CDisabled.class).get());
		ImmutableArray<Entity> unloadableEntities = CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CUnloading.class, CPosition.class).exclude(CDisabled.class).get());
		ArrayList<Entity> toDelete = new ArrayList<Entity>();
		for(Entity e : tileEntities){
			CTile tile = e.getComponent(CTile.class);
			if(tile.myTile.myChunk == chunk) {
				toDelete.add(e);
			}
		}
		for(Entity e : toDelete){
			Utils.removeEntity(e);
		}
		for(int i = 0; i < unloadableEntities.size(); i++){
			Entity e = unloadableEntities.get(i);
			CPosition entityPosition = e.getComponent(CPosition.class);
			float chunkX = (float) Math.floor(entityPosition.position.x / Constants.CHUNK_SIZE) * Constants.CHUNK_SIZE;
			float chunkY = (float) Math.floor(entityPosition.position.y / Constants.CHUNK_SIZE) * Constants.CHUNK_SIZE;
			if(chunkX == chunk.position.x && chunkY == chunk.position.y ){
				CDisabled disabled = CaveGame.instance.pooledEngine.createComponent(CDisabled.class);
				disabled.reason = "chunked";
				e.add(disabled);

				i = -1;
				unloadableEntities = CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CUnloading.class, CPosition.class).exclude(CDisabled.class).get());

			}
		}
	}

	/**
	 * Looks for chunks whose isLoaded value changes, and loads / unloads them
	 * @param node the root node in which to search for chunks
	 */
	public void manageLoading(QuadNode node){
		if(node.size == Constants.CHUNK_SIZE) {
			if (node.holdsChunk.isLoaded != node.holdsChunk.wasLoaded) {
				if (node.holdsChunk.isLoaded) loadChunk(node.holdsChunk);
				else unloadChunk(node.holdsChunk);
			}
		} else {
			if(node.bl != null) manageLoading(node.bl);
			if(node.br != null) manageLoading(node.br);
			if(node.tl != null) manageLoading(node.tl);
			if(node.tr != null) manageLoading(node.tr);
		}
	}

	/**
	 * Updates which chunks are anchored, load / unloads them.
	 */
	public void update(){
		if(needsUpdate){
			markAllChunks(mainNode, false);
			markAnchored();
			manageLoading(mainNode);
			needsUpdate = false;
		}
	}

	/**
	 * Sets the tile at the given world position
	 * @param tile the tile to set
	 * @param x the x-position of the tile
	 * @param y the y-position of the tile
	 */
	public void setTile(Tile tile, int x, int y){
		if(x < 0 || y < 0) return;
		Chunk toChange = getChunkAt(x, y);
		int tileX = (x % Constants.CHUNK_SIZE) / Constants.TILE_SIZE;
		int tileY = (y % Constants.CHUNK_SIZE) / Constants.TILE_SIZE;
		toChange.setTile(tile, tileX, tileY);
	}

	/**
	 * Gets the tile at the current world position
	 * @param x the x-position of the tile
	 * @param y the y-position of the tile
	 * @return the tile at the position
	 */
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0) return null;
		Chunk toChange = getChunkAt(x, y);
		int tileX = (x % Constants.CHUNK_SIZE) / Constants.TILE_SIZE;
		int tileY = (y % Constants.CHUNK_SIZE) / Constants.TILE_SIZE;
		return toChange.getTile(tileX, tileY);
	}

}
