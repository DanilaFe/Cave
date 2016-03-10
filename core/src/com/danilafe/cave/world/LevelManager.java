package com.danilafe.cave.world;

/**
 * LevelManagers help saving / loading LevelData in and out of the engine.
 * @author vanilla
 *
 */
public abstract class LevelManager {

	/**
	 * Saves the data from the current engine.
	 * @return the saved data.
	 */
	public abstract LevelData save();

	/**
	 * Loads the data into the current engine
	 * @param levelData the ldevel data to load from
	 */
	public abstract void load(LevelData levelData);
}
