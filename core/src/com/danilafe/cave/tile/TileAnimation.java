package com.danilafe.cave.tile;

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
	 * Creates a new TileAnimation from the given parameters
	 * @param oneNeighbor the animation when this tile has one neighbor
	 * @param corner the animation when this tile has two adjacent neighbors
	 * @param fourNeighbor the animation when this tile has no exposed sides.
	 * @param twoNeighbors the animation when this tile has two non-adjacent neighbors
	 * @param threeNeighbors the animation when this tile has three neighbors
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

}
