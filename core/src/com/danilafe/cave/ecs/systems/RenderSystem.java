package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Constants;
import com.danilafe.cave.ecs.components.CAnimation;

public class RenderSystem extends IteratingSystem {
	
	SpriteBatch mainBatch;
	SpriteBatch bufferBatch;
	FrameBuffer mainBuffer;
	
	public RenderSystem() {
		super(Family.all(CAnimation.class).get());
		mainBatch = new SpriteBatch();
		bufferBatch = new SpriteBatch();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}
	
	@Override
	public void update(float deltaTime) {
		mainBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		mainBuffer.begin();
		Gdx.gl.glViewport(0, 0, mainBuffer.getWidth(), mainBuffer.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT | Gdx.gl.GL_DEPTH_BUFFER_BIT);
		
		mainBatch.setProjectionMatrix(CaveGame.instance.orthoCam.combined);
		mainBatch.begin();
		
		mainBatch.end();
		super.update(deltaTime);
		mainBuffer.end();
		
		bufferBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bufferBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		bufferBatch.draw(mainBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
		bufferBatch.end();
	}

}
