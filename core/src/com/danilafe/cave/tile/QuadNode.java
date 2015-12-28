package com.danilafe.cave.tile;

/**
 * A data structure to hold world data.
 * @author vanilla
 *
 */
public class QuadNode {

	/**
	 * The top right node.
	 */
	public QuadNode tr;
	/**
	 * The top left node
	 */
	public QuadNode tl;
	/**
	 * The bottom right node
	 */
	public QuadNode br;
	/**
	 * The bottom left node
	 */
	public QuadNode bl;
	/**
	 * The chunk associated with this QuadNode.
	 */
	public Chunk holdsChunk;
	/**
	 * The size of this node.
	 */
	public int size;

}
