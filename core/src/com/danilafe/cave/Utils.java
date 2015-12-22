package com.danilafe.cave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;

/**
 * Utils class to hold utility methods.
 * @author vanilla
 *
 */
public class Utils {

	/**
	 * Checks whether the given edge of the rectangle touches the edge of another rectangle.
	 * @param edgeId the ID of the edge - 0 is up, 1 is right, 2 is down and 3 is left.
	 * @param primary the rectangle whose side is being checked
	 * @param secondary the rectangle checked against
	 * @return true if the given side of the rectangle touches a side of the other rectangle.
	 */
	public static boolean checkEdgeContact(int edgeId, Rectangle primary, Rectangle secondary){
		float bottomY = secondary.y;
		float topY = bottomY + secondary.height;
		float bottomX = secondary.x;
		float topX = bottomX + secondary.width;
		if(edgeId == 0 && (bottomY == primary.y + primary.height) && (primary.x + primary.width > bottomX) && (primary.x < topX)) return true;
		else if(edgeId == 1 && (bottomX == primary.x + primary.width) && (primary.y + primary.height > bottomY) && (primary.y < topY)) return true;
		else if(edgeId == 2 && (topY == primary.y) && (primary.x + primary.width > bottomX) && (primary.x < topX)) return true;
		else if(edgeId == 3 && (topX == primary.x) && (primary.y + primary.height > bottomY) && (primary.y < topY)) return true;
		return false;
	}

	/**
	 * Shader function.
	 * This loads the GLSL shaders for the game.
	 * @param shaderName the name of the shader to load.
	 * @return the loaded and compiled shader program, or null of compilation failed.
	 */
	public static ShaderProgram loadShaders(String shaderName){
		if(shaderName == null || shaderName.equals("") || !Gdx.files.internal("shaders/" + shaderName).exists()) return null;
		ShaderProgram.pedantic = false;
		ShaderProgram newProgram = new ShaderProgram(
				Gdx.files.internal("shaders/" + shaderName + "/vertex.glsl").readString(),
				Gdx.files.internal("shaders/" + shaderName + "/fragment.glsl").readString());
		if (!newProgram.isCompiled()) {
			Gdx.app.error("Shader Loading", newProgram.getLog());
			return null;
		}
		return newProgram;
	}

}
