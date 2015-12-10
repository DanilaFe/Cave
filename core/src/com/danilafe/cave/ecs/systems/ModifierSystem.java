package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danilafe.cave.ecs.components.CModifiable;
import com.danilafe.cave.modifiers.Modifier;

public class ModifierSystem extends IteratingSystem {

	public ModifierSystem() {
		super(Family.all(CModifiable.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CModifiable modifiable = entity.getComponent(CModifiable.class);
		modifiable.modifierContainer.modifierSum.set(0, 0);
		for(Modifier modifier : modifiable.modifierContainer.modifierList){
			modifiable.modifierContainer.modifierSum.add(modifier.modDirection);
		}
	}

}
