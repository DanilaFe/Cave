package com.danilafe.cave.player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.danilafe.cave.Utils;
import com.danilafe.cave.world.Saving;

/**
 * Class describing a player.
 * @author vanilla
 *
 */
public class PlayerStatistic extends Saving {

	/**
	 * Number of games won by the player
	 */
	public int gamesWon;
	/**
	 * Number of games lost by the player
	 */
	public int gamesLost;
	/**
	 * Total games played
	 */
	public int totalGames;
	/**
	 * Name of the player
	 */
	public String name;
	/**
	 * The character meta data of this player
	 */
	public CharacterMeta characterMeta = new CharacterMeta();
	/**
	 * The key binds of this player
	 */
	public KeyBinds keyBinds = new KeyBinds();

	@Override
	public void save(OutputStream out) throws IOException {
		Utils.writeInt(out, gamesWon);
		Utils.writeInt(out, gamesLost);
		Utils.writeInt(out, totalGames);
		Utils.writeString(out, name);
		characterMeta.save(out);
		keyBinds.save(name);
	}

	@Override
	public void load(InputStream in) throws IOException {
		gamesWon = Utils.readInt(in);
		gamesLost = Utils.readInt(in);
		totalGames = Utils.readInt(in);
		name = Utils.readString(in);
		characterMeta.load(in);
		keyBinds.load(name);
	}


}
