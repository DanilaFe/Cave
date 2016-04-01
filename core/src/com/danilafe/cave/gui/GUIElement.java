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
	 * Position in which this element was last rendered
	 */
	public Vector2 renderPosition = new Vector2(0, 0);
	/**
	 * Executed when this GUI element runs
	 */
	public RenderRunnable onRender;
	/**
	 * ArrayList of all children of this element.
	 */
	public ArrayList<GUIElement> children = new ArrayList<GUIElement>();

	/**
	 * Creates a GUIElement from the given parameters
	 * @param select whether this element is selected
	 * @param width with in GUI units of this element
	 * @param height height in GUI element of this element
	 * @param pos_x internal x-position of this element
	 * @param pos_y internal y-position of this element
	 * @param tex non-selected texture of this element
	 * @param selected selected texture of this element
	 * @param worldX the world x-position
	 * @param worldY the world y-position
	 * @param renderRunnable task to execute when this element is rendred
	 * @param children children to add to this element
	 * @return the generated element.
	 */
	public static GUIElement create(boolean select, int width, int height, int pos_x, int pos_y,
			GUITexture tex, GUITexture selected, float worldX, float worldY, RenderRunnable renderRunnable, GUIElement ...children){
		GUIElement element = new GUIElement();
		element.selected = select;
		element.width = width;
		element.height = height;
		element.pos_x = pos_x;
		element.pos_y = pos_y;
		element.guiTexture = tex;
		element.selectedTexture = selected;
		element.worldPos.set(worldX, worldY);
		element.onRender = renderRunnable;
		for(GUIElement child : children){
			element.children.add(child);
		}
		return element;
	}

	/**
	 * Sets a GUIElement to use the given parameters
	 * @param select whether this element is selected
	 * @param width with in GUI units of this element
	 * @param height height in GUI element of this element
	 * @param pos_x internal x-position of this element
	 * @param pos_y internal y-position of this element
	 * @param tex non-selected texture of this element
	 * @param selected selected texture of this element
	 * @param worldX the world x-position
	 * @param worldY the world y-position
	 * @param renderRunnable task to execute when this element is rendred
	 * @param children children to add to this element
	 */
	public void set(GUIElement element, boolean select, int width, int height, int pos_x, int pos_y,
			GUITexture tex, GUITexture selected, float worldX, float worldY, RenderRunnable renderRunnable, GUIElement ...children){
		element.selected = select;
		element.width = width;
		element.height = height;
		element.pos_x = pos_x;
		element.pos_y = pos_y;
		element.guiTexture = tex;
		element.selectedTexture = selected;
		element.worldPos.set(worldX, worldY);
		element.onRender = renderRunnable;
		for(GUIElement child : children){
			element.children.add(child);
		}
	}

}
