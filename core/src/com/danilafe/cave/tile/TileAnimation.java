package com.danilafe.cave.tile;

import com.badlogic.gdx.graphics.Texture;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.animation.AnimationParameter;

/**
 * The animation set of a tile.
 * @author vanilla
 *
 */
public class TileAnimation {

	/**
	 * Animation when this tile has one neighbor
	 */
	public AnimationParameter[] oneNeighbor;
	/**
	 * Animation when this tile has two adjacent neighbors
	 */
	public AnimationParameter[] corner;
	/**
	 * Animation when this tile has two non-adjacent neighbors
	 */
	public AnimationParameter[] twoNeighbors;
	/**
	 * Animation when this tile has three neighbors.
	 */
	public AnimationParameter[] threeNeighbors;
	/**
	 * Animation when this tile has no exposed sides.
	 */
	public AnimationParameter fourNeighbor[];
	/**
	 * Animation when this tile has no neighbors
	 */
	public AnimationParameter noNeighbors[];
	/**
	 * Whether this animation has tile variations
	 */
	public boolean isDifferent = false;
	/**
	 * Creates a new TileAnimation from the given parameters
	 * @param oneNeighbor the animation when this tile has one neighbor
	 * @param corner the animation when this tile has two adjacent neighbors
	 * @param fourNeighbor the animation when this tile has no exposed sides.
	 * @param twoNeighbors the animation when this tile has two non-adjacent neighbors
	 * @param threeNeighbors the animation when this tile has three neighbors
	 * @param noNeighbors the animation when there are no neighbors
	 * @return the created TileAnimation
	 */
	public static TileAnimation create(AnimationParameter[] oneNeighbor, AnimationParameter[] corner, AnimationParameter[] fourNeighbor, AnimationParameter[] twoNeighbors, AnimationParameter[] threeNeighbors, AnimationParameter[] noNeighbors){
		TileAnimation newAnimation = new TileAnimation();
		newAnimation.oneNeighbor = oneNeighbor;
		newAnimation.corner = corner;
		newAnimation.fourNeighbor = fourNeighbor;
		newAnimation.twoNeighbors = twoNeighbors;
		newAnimation.threeNeighbors = threeNeighbors;
		newAnimation.noNeighbors = noNeighbors;

		return newAnimation;
	}

	/**
	 * Create a tile animation from a texture and sizes
	 * @param textureName the name of the texture
	 * @param tileWidth the width of one frame
	 * @param tileHeight the height of one frame
	 * @param timeDelta the delta time between frames
	 * @param isDifferent whether the tiles have variation based on location
	 * @return the tile animation created
	 */
	public static TileAnimation create(String textureName, int tileWidth, int tileHeight, float timeDelta, boolean isDifferent){
		TileAnimation newAnimation = new TileAnimation();
		Texture tex = CaveGame.instance.assetManager.get("textures/" + textureName, Texture.class);

		if(isDifferent){
			int texHeight = tex.getHeight() / 6;
			int texWidth = tex.getWidth();

			int numVariations = texWidth / tileWidth;

			newAnimation.corner = new AnimationParameter[numVariations];
			newAnimation.fourNeighbor = new AnimationParameter[numVariations];
			newAnimation.noNeighbors = new AnimationParameter[numVariations];
			newAnimation.oneNeighbor = new AnimationParameter[numVariations];
			newAnimation.threeNeighbors = new AnimationParameter[numVariations];
			newAnimation.twoNeighbors = new AnimationParameter[numVariations];

			for(int i = 0; i < numVariations; i++){
				newAnimation.fourNeighbor[i] = AnimationParameter.create(textureName, tileWidth * i, 0, tileWidth, texHeight, true, tileWidth, tileHeight, timeDelta);
				newAnimation.threeNeighbors[i] = AnimationParameter.create(textureName, tileWidth * i, texHeight, tileWidth, texHeight, true, tileWidth, tileHeight, timeDelta);
				newAnimation.corner[i] = AnimationParameter.create(textureName, tileWidth * i, texHeight * 2, tileWidth, texHeight, true, tileWidth, tileHeight, timeDelta);
				newAnimation.twoNeighbors[i] = AnimationParameter.create(textureName, tileWidth * i, texHeight * 3, tileWidth, texHeight, true, tileWidth, tileHeight, timeDelta);
				newAnimation.oneNeighbor[i] = AnimationParameter.create(textureName, tileWidth * i, texHeight * 4, tileWidth, texHeight, true, tileWidth, tileHeight, timeDelta);
				newAnimation.noNeighbors[i] = AnimationParameter.create(textureName, tileWidth * i, texHeight * 5, tileWidth, texHeight, true, tileWidth, tileHeight, timeDelta);
			}
		} else {
			int numVariations = tex.getWidth() / tileWidth;

			newAnimation.corner = new AnimationParameter[numVariations];
			newAnimation.fourNeighbor = new AnimationParameter[numVariations];
			newAnimation.noNeighbors = new AnimationParameter[numVariations];
			newAnimation.oneNeighbor = new AnimationParameter[numVariations];
			newAnimation.threeNeighbors = new AnimationParameter[numVariations];
			newAnimation.twoNeighbors = new AnimationParameter[numVariations];

			for(int i = 0; i < numVariations; i++){
				AnimationParameter param = AnimationParameter.create(textureName, tex.getWidth() / numVariations * i, 0, tex.getWidth() / numVariations, tex.getHeight(), true, tileWidth, tileHeight, timeDelta);
				newAnimation.fourNeighbor[i] = param;
				newAnimation.threeNeighbors[i] = param;
				newAnimation.twoNeighbors[i] = param;
				newAnimation.corner[i] = param;
				newAnimation.oneNeighbor[i] = param;
				newAnimation.noNeighbors[i] = param;
			}
		}

		newAnimation.isDifferent = isDifferent;

		return newAnimation;
	}

}
