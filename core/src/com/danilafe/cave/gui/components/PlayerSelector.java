package com.danilafe.cave.gui.components;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Constants;
import com.danilafe.cave.gui.GUIElement;
import com.danilafe.cave.gui.GUITexture;
import com.danilafe.cave.gui.RenderRunnable;
import com.danilafe.cave.player.PlayerStatistic;

public class PlayerSelector extends GUIElement {

	/**
	 * The ID of the loaded player
	 */
	public int playerId = -1;
	/**
	 * Player statistic loaded on create
	 */
	public PlayerStatistic playerStatitic = null;

	/**
	 * Default GUI height of this element
	 */
	public static final int DEFAULT_HEIGHT = 4;
	/**
	 * Default GUI width of this element
	 */
	public static final int DEFAULT_WIDTH = 4;
	/**
	 * Cached texture for rendering
	 */
	public static Texture questionmarkTexture;
	/**
	 * Renderable that takes care of render the element
	 */
	public static RenderRunnable renderRunnable = new RenderRunnable() {
		@Override
		public void render(SpriteBatch renderTo, Object m) {
			PlayerSelector me = (PlayerSelector) m;
			if(me.playerStatitic == null) {
				if(questionmarkTexture == null)
					questionmarkTexture = CaveGame.instance.assetManager.get("textures/symbol_question.png", Texture.class);
				renderTo.draw(questionmarkTexture,
						me.renderPosition.x + (me.width * Constants.GUI_UNIT_SIZE - questionmarkTexture.getWidth()) / 2,
						me.renderPosition.y + (me.height * Constants.GUI_UNIT_SIZE - questionmarkTexture.getHeight()) / 2);
			} else {
				// Render Player
			}
		}
	};

	/**
	 * Creates a new player selector for the given player number
	 * @param playerNumber the player number to use
	 */
	public PlayerSelector(int playerNumber) {
		playerId = playerNumber;
		FileHandle playerFile = Gdx.files.local("player_" + playerNumber);
		if(playerFile.exists()) {
			try {
				playerStatitic = new PlayerStatistic();
				System.out.println(playerFile.name());
				playerStatitic.load(playerFile.read());
				System.out.println("Loaded");
			} catch (IOException e) { e.printStackTrace(); }
		}
		GUITexture texture = CaveGame.instance.creationManager.guiTextures.get("windowTexture");
		GUITexture selectedTexture = CaveGame.instance.creationManager.guiTextures.get("windowTextureSelected");
		set(this, false, DEFAULT_WIDTH, DEFAULT_HEIGHT, 0, 0, texture, selectedTexture, 0, 0, renderRunnable);
	}

}
