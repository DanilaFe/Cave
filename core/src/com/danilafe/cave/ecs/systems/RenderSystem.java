package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.ecs.components.CAnimation;

public class RenderSystem extends IteratingSystem {

	public SpriteBatch mainSpriteBatch;
	public FrameBuffer mainFrameBuffer;
	public ShaderProgram shaderProgram;
	
	public RenderSystem() {
		super(Family.all(CAnimation.class).get());
		mainSpriteBatch = new SpriteBatch();
		mainFrameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		shaderProgram = CaveGame.loadShaders("default");
		
		mainSpriteBatch.setShader(shaderProgram);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}
	
	@Override
	public void update(float deltaTime) {
		/*
		 * Clear the buffer, to which everything is drawn in the processEntity 
		 */
		mainFrameBuffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		mainFrameBuffer.end();
		
		super.update(deltaTime);
		
		/*
		 * Clear the actual OpenGl context, and draw the buffer onto it.
		 */
		mainSpriteBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		mainSpriteBatch.draw(mainFrameBuffer.getColorBufferTexture(), 0, 0);
		mainSpriteBatch.end();
	}

}
