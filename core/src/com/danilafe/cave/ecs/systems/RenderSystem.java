package com.danilafe.cave.ecs.systems;

import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Constants;
import com.danilafe.cave.Utils;
import com.danilafe.cave.animation.Animation;
import com.danilafe.cave.ecs.components.CAnimation;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CPosition;
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
	public SpriteBatch textureBatch;
	/**
	 * The batch used to render buffer contents.
	 */
	public SpriteBatch shaderBatch;
	/**
	 * The batch used to render normal maps
	 */
	public SpriteBatch normalBatch;
	/**
	 * Batch used to draw to a screen
	 */
	public SpriteBatch screenBatch;
	/**
	 * The shader used by the bufferBatch
	 */
	public ShaderProgram shaderProgram;
	/**
	 * The framebuffer used to render everything before shader is applied.
	 */
	public FrameBuffer mainBuffer;
	/**
	 * Buffer used for normal maps
	 */
	public FrameBuffer textureBuffer;
	/**
	 * The buffer used with the shader
	 */
	public FrameBuffer shaderBuffer;
	/**
	 * Class to manage lights
	 */
	public LightManager lightManager;
	/**
	 * Rotated Textures
	 */
	public HashMap<Texture, HashMap<Integer, Texture>> rotatedTextures;

	public RenderSystem() {
		super(Family.all(CAnimation.class, CPosition.class).exclude(CDisabled.class).get());
		textureBatch = new SpriteBatch();
		shaderBatch = new SpriteBatch();
		normalBatch = new SpriteBatch();
		screenBatch = new SpriteBatch();
		shaderProgram = Utils.loadShaders("debug");
		lightManager = new LightManager();
		rotatedTextures = new HashMap<Texture, HashMap<Integer, Texture>>();

		shaderBatch.setShader(shaderProgram);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CAnimation animation = entity.getComponent(CAnimation.class);
		CPosition position = entity.getComponent(CPosition.class);
		Animation animationObject = animation.animationQueue.animationQueue.peek();
		if (animationObject != null){
			TextureRegion toRender = animationObject.getTextureAt(animationObject.texIndex);
			textureBatch.draw(toRender, Math.round(position.position.x - toRender.getRegionWidth() / 2), Math.round(position.position.y - toRender.getRegionHeight() / 2), toRender.getRegionWidth() / 2, toRender.getRegionHeight() / 2, toRender.getRegionWidth(), toRender.getRegionHeight(), 1 * ((animation.flipHorizontal) ? -1 : 1), 1 * ((animation.flipVertical) ? -1 : 1), -animation.rotation);
		}
		animation.animationQueue.update(deltaTime);
	}

	@Override
	public void update(float deltaTime) {
		int orthoCamWidth = Math.round(CaveGame.instance.orthoCam.viewportWidth);
		int orthoCamHeight = Math.round(CaveGame.instance.orthoCam.viewportHeight);
		int downscale = (int) Math.round(Math.ceil((float) Gdx.graphics.getWidth() / 480));
		int bufferSizeW = Gdx.graphics.getWidth() / downscale;
		int bufferSizeH = Gdx.graphics.getHeight() / downscale;

		mainBuffer = new FrameBuffer(Format.RGBA8888, bufferSizeW, bufferSizeH, false);
		mainBuffer.begin();
		Gdx.gl.glClearColor(1F, 1F, 1F, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		textureBatch.setProjectionMatrix(CaveGame.instance.orthoCam.combined);
		textureBatch.begin();
		super.update(deltaTime);
		textureBatch.end();
		mainBuffer.end();

		textureBuffer = new FrameBuffer(Format.RGBA8888, bufferSizeW, bufferSizeH, false);
		textureBuffer.begin();
		Gdx.gl.glClearColor(.5F, .5F, 1F, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		if(Constants.NORMAL){
			normalBatch.setProjectionMatrix(CaveGame.instance.orthoCam.combined);
			normalBatch.begin();
			for(Entity e : getEntities()){
				CAnimation animation = e.getComponent(CAnimation.class);
				CPosition position = e.getComponent(CPosition.class);
				Animation animationObject = animation.animationQueue.animationQueue.peek();
				if (animationObject != null && animationObject.animationParameter.normalTextures != null){
					TextureRegion toRender = animationObject.getNormalAt(animationObject.texIndex);
					Texture originalTexture = toRender.getTexture();
					if(animation.rotation != 0) toRender.setTexture(getRotatedTexture(originalTexture, animation.rotation));
					normalBatch.draw(toRender, Math.round(position.position.x - toRender.getRegionWidth() / 2), Math.round(position.position.y - toRender.getRegionHeight() / 2), toRender.getRegionWidth() / 2, toRender.getRegionHeight() / 2, toRender.getRegionWidth(), toRender.getRegionHeight(), 1 * ((animation.flipHorizontal) ? -1 : 1), 1 * ((animation.flipVertical) ? -1 : 1), -animation.rotation);
					toRender.setTexture(originalTexture);
				}
			}
			normalBatch.end();
		}
		textureBuffer.end();


		shaderBuffer = new FrameBuffer(Format.RGBA8888, bufferSizeW, bufferSizeH, false);
		shaderBuffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		shaderBatch.getProjectionMatrix().setToOrtho2D(0, 0, shaderBuffer.getWidth(), shaderBuffer.getHeight());
		shaderBatch.begin();
		Texture normalTexture = textureBuffer.getColorBufferTexture();
		Texture regularTexture = mainBuffer.getColorBufferTexture();
		normalTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		regularTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		normalTexture.bind(1);
		Gdx.gl.glActiveTexture(Gdx.gl.GL_TEXTURE0);
		Gdx.app.debug("Lights", "Rendering " + lightManager.lights.size() + " lights");
		shaderProgram.setUniformi("u_normalTexture", 1);
		shaderProgram.setUniformi("u_textureWidth", orthoCamWidth);
		shaderProgram.setUniformi("u_textureHeight", orthoCamHeight);
		shaderProgram.setUniformf("u_texOffsetX", (CaveGame.instance.orthoCam.position.x - CaveGame.instance.orthoCam.viewportWidth / 2));
		shaderProgram.setUniformf("u_texOffsetY", (CaveGame.instance.orthoCam.position.y - CaveGame.instance.orthoCam.viewportHeight / 2));
		lightManager.sortByDistance(new Vector2(CaveGame.instance.orthoCam.position.x, CaveGame.instance.orthoCam.position.y));
		shaderProgram.setUniform3fv("u_ambient", lightManager.ambientLight, 0, 3);
		shaderProgram.setUniformi("u_numLights", (lightManager.maxLights < lightManager.lights.size()) ? lightManager.maxLights : lightManager.lights.size());
		for(int i = 0; i < lightManager.lights.size() && i < lightManager.maxLights; i++){
			lightManager.lights.get(i).updateFlicker();
			shaderProgram.setUniform3fv("u_lightColors[" + i + "]", lightManager.lights.get(i).currentRgb , 0, 3);
			shaderProgram.setUniform3fv("u_lightProps[" + i + "]", lightManager.lights.get(i).getPropertyArray(), 0, 3);
		}
		shaderBatch.draw(regularTexture, 0, 0);
		shaderBatch.end();
		shaderBuffer.end();

		screenBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		screenBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shaderBuffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		screenBatch.draw(shaderBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenBatch.end();

		shaderBuffer.dispose();
		mainBuffer.dispose();
		textureBuffer.dispose();
	}

	public Texture getRotatedTexture(Texture sourceTexture, int rotation){
		if(rotatedTextures.get(sourceTexture) == null) rotatedTextures.put(sourceTexture, new HashMap<Integer, Texture>());
		HashMap<Integer, Texture> rotatedVersions = rotatedTextures.get(sourceTexture);
		if(rotatedVersions.containsKey(rotation)) return rotatedVersions.get(rotation);
		else {
			TextureData textureData = sourceTexture.getTextureData();
			if(!textureData.isPrepared()) textureData.prepare();
			Pixmap newPixmap = textureData.consumePixmap();
			Vector2 tmp = new Vector2();
			for(int w = 0; w < newPixmap.getWidth(); w++){
				for(int h = 0; h < newPixmap.getHeight(); h++){
					int pixelColor = newPixmap.getPixel(w, h);
					int blue = (pixelColor >> 8) & 0xFF;
					tmp.set((pixelColor >> 24) & 0xFF, (pixelColor >> 16) & 0xFF);
					tmp.sub(255 / 2, 255 / 2);
					tmp.rotate(rotation);
					tmp.add(255 / 2, 255 / 2);
					int newColor = (((int) tmp.x) << 24) | (((int) tmp.y) << 16) | (blue << 8) | 255;
					newPixmap.drawPixel(w, h, newColor);
				}
			}
			Texture newTexture = new Texture(newPixmap);
			newPixmap.dispose();
			rotatedVersions.put(rotation, newTexture);
			return newTexture;
		}
	}

}
