package com.danilafe.cave.tile;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Constants;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CTile;

public class MapManager {

	/**
	 * The largest existing quad node
	 */
	public QuadNode mainNode = new QuadNode();

	public ArrayList<ChunkAnchor> anchors = new ArrayList<ChunkAnchor>();

	public MapManager() {
		mainNode.size = Constants.CHUNK_SIZE;
	}

	public Chunk getChunkAt(float x, float y){
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

	public void loadChunk(Chunk chunk){
		Gdx.app.log("World Tree", "Loading Chunk");
		for(int x = 0; x < Constants.CHUNK_SIZE / Constants.TILE_SIZE; x++){
			for(int y = 0; y < Constants.CHUNK_SIZE/ Constants.TILE_SIZE; y++){
				Tile toCreate = chunk.getTile(x, y);
				if(toCreate == null) continue;
				Entity createdEntiy = CaveGame.instance.creationManager.entityDescriptors.get(toCreate.tileParameter.entityType).create(chunk.position.x + x * Constants.TILE_SIZE, chunk.position.y + y * Constants.TILE_SIZE);
				CTile tile = CaveGame.instance.pooledEngine.createComponent(CTile.class);
				tile.myTile = toCreate;
				createdEntiy.add(tile);
				CaveGame.instance.pooledEngine.addEntity(createdEntiy);
			}
		}
	}

	public void unloadChunk(Chunk chunk){
		Gdx.app.log("World Tree", "Unloading Chunk");
		ImmutableArray<Entity> tileEntities = CaveGame.instance.pooledEngine.getEntitiesFor(Family.all(CTile.class).get());
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
	}

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

	public void update(){
		markAllChunks(mainNode, false);
		markAnchored();
		manageLoading(mainNode);
	}

}
