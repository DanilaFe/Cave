package com.danilafe.cave.player;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * KeyBinds class that stores data about key configurations
 * @author vanilla
 *
 */
public class KeyBinds {

	/**
	 * Cached preferences
	 */
	public Preferences preferences = null;
	/**
	 * Cached name of lastLoaded string
	 */
	public String lastLoad = "";

	/**
	 * The mapped keys.
	 */
	public HashMap<String, Integer> mappedKeys = new HashMap<String, Integer>();

	/**
	 * Creates a new mapping for the given key.
	 * @param s the key to map
	 * @param i the keycode to map to
	 */
	public void setKey(String s, int i) { mappedKeys.put(s, i); }
	/**
	 * Retrieves the keycode for the given key.
	 * @param s the key to retreive
	 * @return the produced keycode
	 */
	public int getKeycodeFor(String s) { return mappedKeys.get(s); }

	/**
	 * Checks if the given key is pressed
	 * @param s the key to check for
	 * @return true if it's pressed.
	 */
	public boolean keyPressed(String s) {
		return Gdx.input.isKeyPressed(mappedKeys.get(s));
	}

	/**
	 * Checks if the given key is just pressed
	 * @param s the key to check for
	 * @return true if it's just pressed
	 */
	public boolean keyJustPressed(String s) {
		return Gdx.input.isKeyJustPressed(mappedKeys.get(s));
	}

	/**
	 * Saves these settings using the LibGdx preferences feature
	 * @param characterName the name of the character whose file to associate.
	 */
	public void save(String characterName){
		Preferences pref = getPreferences(characterName);
		pref.put(mappedKeys);
		pref.flush();
	}

	/**
	 * Loads these settings using the LibGdx preferences feature
	 * @param characterName the name of the character whose file to associate.
	 */
	public void load(String characterName){
		Preferences pref = getPreferences(characterName);
		for(String key : pref.get().keySet()){
			mappedKeys.put(key, pref.getInteger(key));
		}
	}

	/**
	 * Gets the preferences (using cache to avoid frequent requests to getPreferences)
	 * @param characterName
	 * @return the loaded preferences
	 */
	public Preferences getPreferences(String characterName){
		String load = "keyconfig_" + characterName;

		Preferences pref = (lastLoad.equals(load) && preferences != null) ? preferences : Gdx.app.getPreferences(load);
		lastLoad = load;
		preferences = pref;

		return pref;
	}

}
