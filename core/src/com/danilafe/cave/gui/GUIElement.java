package com.danilafe.cave.gui;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

/**
 * GUIElement used for displaying GUI onto the screen.
 * @author vanilla
 *
 */
public class GUIElement {

	/**
	 * Whether this element is selected
	 */
	public boolean selected;
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
	 * The background texture of this element if it's selected
	 */
	public GUITexture selectedTexture;
	/**
	 * The position of this element inside the world. Only if it's top level.
	 */
	public Vector2 worldPos = new Vector2(0, 0);
	/**
	 * Executed when this GUI element runs
	 */
	public RenderRunnable onRender;
	/**
	 * ArrayList of all children of this element.
	 */
	public ArrayList<GUIElement> children = new ArrayList<GUIElement>();


}
