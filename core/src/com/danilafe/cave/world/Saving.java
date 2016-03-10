package com.danilafe.cave.world;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Saving {

	public void fullSave(OutputStream out) throws IOException {
		out.write(getClass().getName().getBytes());
		out.write(':');
		save(out);
	}

	public abstract void save(OutputStream out) throws IOException;
	public abstract void load(InputStream in) throws IOException;

}
