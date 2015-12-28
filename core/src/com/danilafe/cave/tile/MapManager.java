package com.danilafe.cave.tile;

import com.badlogic.gdx.Gdx;
import com.danilafe.cave.Constants;

public class MapManager {

	/**
	 * The largest existing quad node
	 */
	public QuadNode mainNode;

	public MapManager() {
		mainNode = new QuadNode();
		mainNode.size = Constants.CHUNK_SIZE;
	}

	public Chunk getChunkAt(float x, float y){
		int chunkX = (int) Math.ceil(x / Constants.CHUNK_SIZE);
		int chunkY = (int) Math.ceil(y / Constants.CHUNK_SIZE);
		int biggetValue = (chunkX > chunkY) ? chunkX : chunkY;
		int requiredPower = (int) (Math.ceil(Math.log(biggetValue) / Math.log(2)));
		int requiredSize = (int) Math.pow(2, requiredPower) * Constants.CHUNK_SIZE;

		Gdx.app.debug("World Tree", "Required Size " + requiredSize);

		mainNode = increaseUntilSize(requiredSize, mainNode);

		return accessChunk(mainNode, x, y);
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

	public Chunk accessChunk(QuadNode node, float relativeX, float relativeY){
		if(node.size == Constants.CHUNK_SIZE) return node.holdsChunk;
		boolean secondX = relativeX > node.size / 2;
		boolean secondY = relativeY > node.size / 2;

		if(secondX) relativeX -= node.size / 2;
		if(secondY) relativeY -= node.size / 2;

		if(secondX && secondY) {
			if(node.tr == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent top-right node. Creating new.");
				node.tr = new QuadNode();
				node.tr.size = node.size / 2;
			}
			return accessChunk(node.tr, relativeX, relativeY);
		} else if(secondX){
			if(node.br == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent botoom-right node. Creating new.");
				node.br = new QuadNode();
				node.br.size = node.size / 2;
			}
			return accessChunk(node.br, relativeX, relativeY);
		} else if(secondY){
			if(node.tl == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent top-left node. Creating new.");
				node.tl = new QuadNode();
				node.tl.size = node.size / 2;
			}
			return accessChunk(node.tl, relativeX, relativeY);
		} else {
			if(node.bl == null) {
				Gdx.app.debug("World Tree", "Acces to non-existent bottom-left node. Creating new.");
				node.bl = new QuadNode();
				node.bl.size = node.size / 2;
			}
			return accessChunk(node.bl, relativeX, relativeY);
		}
	}

}
