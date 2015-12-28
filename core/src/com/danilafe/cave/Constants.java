package com.danilafe.cave;

/**
 * Holds values that are constants for the game. Prevents hardcoding.
 * @author vanilla
 *
 */
public class Constants {

	/**
	 * The width of the in-game world view. Height scales with window size.
	 */
	public static final float CAMERA_WIDTH = 100;
	/**
	 * The default gravity.
	 */
	public static final float DEFAULT_GRAVITY = -500;
	/**
	 * Whether the game should start in debug mode or not.
	 */
	public static final boolean DEBUG = true;
	/**
	 * Whether normal maps should be used.
	 */
	public static final boolean NORMAL = true;
	/**
	 * The size of a regular tile
	 */
	public static final int TILE_SIZE = 8;
	/**
	 * The size (w/h) of one chunk.
	 */
	public static final int CHUNK_SIZE = TILE_SIZE * 8;

}
