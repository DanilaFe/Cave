package com.danilafe.cave.gui;

import java.util.ArrayList;

/**
 * GUIElement used for displaying GUI onto the screen.
 * @author vanilla
 *
 */
public class GUIElement {

	/**
	 * Width of GUI element
	 */
	public int width = 0;
	/**
	 * Height of GUI element
	 */
	public int height = 0;
	/**
	 * The x-position of this element, but only if it's a child of another element
	 */
	public int pos_x = 0;
	/**
	 * The y-position of this element, but only if it's a child of another element
	 */
	public int pos_y = 0;
	/**
	 * the background texture of this element
	 */
	public GUITexture guiTexture;

	/**
	 * ArrayList of all children of this element.
	 */
	public ArrayList<GUIElement> children = new ArrayList<GUIElement>();


}
