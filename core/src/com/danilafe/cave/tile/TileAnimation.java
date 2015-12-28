package com.danilafe.cave.tile;

import com.danilafe.cave.animation.Animation;

/**
 * The animation set of a tile.
 * @author vanilla
 *
 */
public class TileAnimation {

	/**
	 * Animation when this tile has one neighbor
	 */
	public Animation[] oneNeighbor;
	/**
	 * Animation when this tile has two adjacent neighbors
	 */
	public Animation[] corner;
	/**
	 * Animation when this tile has two non-adjacent neighbors
	 */
	public Animation[] twoNeighbors;
	/**
	 * Animation when this tile has three neighbors.
	 */
	public Animation[] threeNeighbors;
	/**
	 * Animation when this tile has no exposed sides.
	 */
	public Animation fourNeighbor[];
	/**
	 * Creates a new TileAnimation from the given parameters
	 * @param oneNeighbor the animation when this tile has one neighbor
	 * @param corner the animation when this tile has two adjacent neighbors
	 * @param fourNeighbor the animation when this tile has no exposed sides.
	 * @param twoNeighbors the animation when this tile has two non-adjacent neighbors
	 * @param threeNeighbors the animation when this tile has three neighbors
	 * @return the created TileAnimation
	 */
	public static TileAnimation create(Animation[] oneNeighbor, Animation[] corner, Animation[] fourNeighbor, Animation[] twoNeighbors, Animation[] threeNeighbors){
		TileAnimation newAnimation = new TileAnimation();
		newAnimation.oneNeighbor = oneNeighbor;
		newAnimation.corner = corner;
		newAnimation.fourNeighbor = fourNeighbor;
		newAnimation.twoNeighbors = twoNeighbors;
		newAnimation.threeNeighbors = threeNeighbors;

		return newAnimation;
	}

}
