package com.danilafe.cave.world;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Abstract class that can indepdendently save its data
 * @author vanilla
 *
 */
public abstract class Saving {

	/**
	 * Saves the class' name as well as its data, so that it can be loaded without knowing
	 * what it is.
	 * @param out the stream to save to
	 * @throws IOException
	 */
	public void fullSave(OutputStream out) throws IOException {
		out.write(getClass().getName().getBytes());
		out.write(':');
		save(out);
	}

	/**
	 * Saves this class' data
	 * @param out the stream to save to
	 * @throws IOException
	 */
	public abstract void save(OutputStream out) throws IOException;

	/**
	 * Reads this class' data
	 * @param in the stream to read from
	 * @throws IOException
	 */
	public abstract void load(InputStream in) throws IOException;

}
