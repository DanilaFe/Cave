package com.danilafe.game.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.danilafe.cave.ecs.components.CPosition;
import com.danilafe.cave.ecs.components.CSpeed;
import com.danilafe.cave.ecs.systems.PositionSystem;

public class BasicTests {

	@Test
	public void testPosition(){
		Engine testEngine = new Engine();
		Entity entity = new Entity();
		CPosition position = new CPosition();

		entity.add(position);
		testEngine.addEntity(entity);

		position.position.set(0, 0);
		testEngine.update(1);

		assertTrue(position.position.x == 0);
		assertTrue(position.position.y == 0);
	}

	@Test
	public void testSpeed(){
		Vector2 originalSpeed = new Vector2().set(5, .2F);
		Vector2 originalPosition = new Vector2().set(5, 5);

		PositionSystem speedSystem = new PositionSystem();
		Engine testEngine = new Engine();
		Entity entity = new Entity();
		CPosition position = new CPosition();
		CSpeed speed = new CSpeed();

		entity.add(speed);
		entity.add(position);
		testEngine.addEntity(entity);
		testEngine.addSystem(speedSystem);

		position.position.set(originalPosition);
		speed.speed.set(originalSpeed);
		testEngine.update(.5F);

		assertTrue(position.position.equals(originalPosition.cpy().add(originalSpeed.cpy().scl(.5F))));
		assertTrue(speed.speed.equals(originalSpeed));

		testEngine.update(.5F);
		assertTrue(position.position.equals(originalPosition.cpy().add(originalSpeed.cpy().scl(1F))));
		assertTrue(speed.speed.equals(originalSpeed));
	}

}
