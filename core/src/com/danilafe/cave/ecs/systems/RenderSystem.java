package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.ecs.components.CAnimation;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.lights.Light;
import com.danilafe.cave.lights.LightManager;

/**
 * RenderSystem - renders the game.
 * @author vanilla
 *
 */
public class RenderSystem extends IteratingSystem {
	
	/**
	 * The batch used in the FrameBuffer. Can have its own shader.
	 */
	SpriteBatch mainBatch;
	/**
	 * The batch used to render buffer contents.
	 */
	SpriteBatch bufferBatch;
	SpriteBatch normalBatch;
	/**
	 * The shader used by the bufferBatch
	 */
	ShaderProgram shaderProgram;
	/**
	 * The framebuffer used to render everything before shader is applied.
	 */
	FrameBuffer mainBuffer;
	FrameBuffer normalBuffer;
	LightManager lightManager;
	
	public RenderSystem() {
		super(Family.all(CAnimation.class, CPosition.class).get());
		mainBatch = new SpriteBatch();
		bufferBatch = new SpriteBatch();
		normalBatch = new SpriteBatch();
		shaderProgram = CaveGame.loadShaders("debug");
		lightManager = new LightManager();
		lightManager.lights.add(Light.create(50, 24, 200, 1F, .85F, .7F));
		
		bufferBatch.setShader(shaderProgram);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CAnimation animation = entity.getComponent(CAnimation.class);
		CPosition position = entity.getComponent(CPosition.class);
		Animation animationObject = animation.animationQueue.animationQueue.peek();
		if (animationObject != null){
			TextureRegion toRender = animationObject.getTextureAt(animationObject.texIndex);
			mainBatch.draw(toRender, Math.round(position.position.x - toRender.getRegionWidth() / 2), Math.round(position.position.y - toRender.getRegionHeight() / 2));
		}
		animation.animationQueue.update(deltaTime);
	}
	
	@Override
	public void update(float deltaTime) {
		mainBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		mainBuffer.begin();
		Gdx.gl.glViewport(0, 0, mainBuffer.getWidth(), mainBuffer.getHeight());
		Gdx.gl.glClearColor(1F, 1F, 1F, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT | Gdx.gl.GL_DEPTH_BUFFER_BIT);	
		mainBatch.setProjectionMatrix(CaveGame.instance.orthoCam.combined);	
		mainBatch.begin();
		super.update(deltaTime);
		mainBatch.end();
		mainBuffer.end();
		
		normalBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		normalBuffer.begin();
		Gdx.gl.glViewport(0, 0, normalBuffer.getWidth(), normalBuffer.getHeight());
		Gdx.gl.glClearColor(.5F, .5F, 1F, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT | Gdx.gl.GL_DEPTH_BUFFER_BIT);	
		normalBatch.setProjectionMatrix(CaveGame.instance.orthoCam.combined);	
		normalBatch.begin();
		for(Entity e : getEntities()){
			CAnimation animation = e.getComponent(CAnimation.class);
			CPosition position = e.getComponent(CPosition.class);
			Animation animationObject = animation.animationQueue.animationQueue.peek();
			if (animationObject != null){
				TextureRegion toRender = animationObject.getNormalAt(animationObject.texIndex);
				normalBatch.draw(toRender, Math.round(position.position.x - toRender.getRegionWidth() / 2), Math.round(position.position.y - toRender.getRegionHeight() / 2));
			}
		}
		normalBatch.end();
		normalBuffer.end();
		
		bufferBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bufferBatch.begin();
		normalBuffer.getColorBufferTexture().bind(1);
		Gdx.gl.glActiveTexture(Gdx.gl.GL_TEXTURE0);
		Gdx.app.debug("Lights", "Rendering " + lightManager.lights.size() + " lights");
		shaderProgram.setUniformi("u_normalTexture", 1);
		shaderProgram.setUniformi("u_textureWidth", (int) CaveGame.instance.orthoCam.viewportWidth);
		shaderProgram.setUniformi("u_textureHeight", (int) CaveGame.instance.orthoCam.viewportHeight);
		shaderProgram.setUniformi("u_texOffsetX", (int) (CaveGame.instance.orthoCam.position.x - CaveGame.instance.orthoCam.viewportWidth / 2));
		shaderProgram.setUniformi("u_texOffsetY", (int) (CaveGame.instance.orthoCam.position.y - CaveGame.instance.orthoCam.viewportHeight / 2));
		lightManager.sortByDistance(new Vector2(CaveGame.instance.orthoCam.position.x, CaveGame.instance.orthoCam.position.y));
		shaderProgram.setUniformi("u_numLights", (lightManager.maxLights < lightManager.lights.size()) ? lightManager.maxLights : lightManager.lights.size());
		for(int i = 0; i < lightManager.lights.size() && i < lightManager.maxLights; i++){
			shaderProgram.setUniform3fv("u_lightColors[" + i + "]", lightManager.lights.get(i).rgb , 0, 3);
			shaderProgram.setUniform3fv("u_lightProps[" + i + "]", lightManager.lights.get(i).getPropertyArray(), 0, 3);
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		bufferBatch.draw(mainBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
		bufferBatch.end();
		
		mainBuffer.dispose();
		normalBuffer.dispose();
	}

}
