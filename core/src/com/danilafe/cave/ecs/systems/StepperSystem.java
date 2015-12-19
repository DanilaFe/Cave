package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CStepper;

/**
 * StepperSystem - calls the update function of all stepper component.
 * @author vanilla
 *
 */
public class StepperSystem extends IteratingSystem {

	/**
	 * Creates a new StepperSystem.
	 */
	public StepperSystem() {
		super(Family.all(CStepper.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CStepper stepper = entity.getComponent(CStepper.class);
		if(stepper.runnable != null) stepper.runnable.update(entity, getEngine(), deltaTime);
	}

}
