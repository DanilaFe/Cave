package com.danilafe.cave.ecs.systems;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CFrictionCause;
import com.danilafe.cave.ecs.components.CFrictionObject;
import com.danilafe.cave.ecs.components.CSpeed;

public class FrictionSystem extends FamilySystem {

	public FrictionSystem() {
		super(Family.all(CSpeed.class, CFrictionObject.class, CBounds.class).get(), Family.all(CBounds.class, CFrictionCause.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for(int i = 0; i < entitiesA.size(); i ++){
			Entity fe = entitiesA.get(i);
			CSpeed fes = fe.getComponent(CSpeed.class);
			CBounds feb = fe.getComponent(CBounds.class);
			for(int j = 0; j < entitiesB.size(); j++){
				Entity oe = entitiesB.get(j);
				CBounds oeb = oe.getComponent(CBounds.class);
				CFrictionCause oef = oe.getComponent(CFrictionCause.class);
				if ((oeb.bounds.x == feb.bounds.x + feb.bounds.width
						&& (feb.bounds.y + feb.bounds.height >= oeb.bounds.y && feb.bounds.y <= oeb.bounds.y + oeb.bounds.height))
					|| (feb.bounds.x == oeb.bounds.x + oeb.bounds.width
							&& (oeb.bounds.y + oeb.bounds.height >= feb.bounds.y && oeb.bounds.y <= feb.bounds.y + feb.bounds.height))){
					Gdx.app.debug("Friction Detection", "Vertical Friction");
					fes.speed.y *= Math.pow(oef.frictionMultiplier.y, deltaTime);
				}
				if ((oeb.bounds.y == feb.bounds.y + feb.bounds.height
						&& (feb.bounds.x + feb.bounds.width >= oeb.bounds.x && feb.bounds.x <= oeb.bounds.x + oeb.bounds.width))
					|| (feb.bounds.y == oeb.bounds.y + oeb.bounds.height
							&& (oeb.bounds.x + oeb.bounds.width >= feb.bounds.x && oeb.bounds.x <= feb.bounds.x + feb.bounds.width))){
					Gdx.app.debug("Friction Detection", "Horizontal Friction");
					fes.speed.x *= Math.pow(oef.frictionMultiplier.x, deltaTime);
				}
			}
		}
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}
	
}
