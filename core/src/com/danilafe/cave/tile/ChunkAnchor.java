package com.danilafe.cave.tile;

import com.badlogic.gdx.math.Vector2;

/**
 * A chunk anchor is something that keeps the chunks around it loaded.
 * @author vanilla
 *
 */
public class ChunkAnchor {

	/**
	 * The position of this chunk anchor
	 */
	public Vector2 position = new Vector2();
	/**
	 * The range of this chunk anchor.
	 */
	public int range;

}
