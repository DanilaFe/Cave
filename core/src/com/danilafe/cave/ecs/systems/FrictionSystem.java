package com.danilafe.cave.ecs.systems;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.danilafe.cave.Utils;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CFrictionCause;
import com.danilafe.cave.ecs.components.CFrictionObject;
import com.danilafe.cave.ecs.components.CSpeed;

/**
 * FrictionSystem - Applies friction to entities with the FrictionObject flag.
 * The friction is caused by FrictionCause entities and is applied only if the entities' sides touch.
 * @author vanilla
 *
 */
public class FrictionSystem extends FamilySystem {

	/**
	 * Creates a new FrictionSystem
	 */
	public FrictionSystem() {
		super(Family.all(CSpeed.class, CFrictionObject.class, CBounds.class).exclude(CDisabled.class).get(), Family.all(CBounds.class, CFrictionCause.class).exclude(CDisabled.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for(int i = 0; i < entitiesA.size(); i ++){
			Entity fe = entitiesA.get(i);
			CSpeed fes = fe.getComponent(CSpeed.class);
			CBounds feb = fe.getComponent(CBounds.class);
			CFrictionObject ffo = fe.getComponent(CFrictionObject.class);
			for(int j = 0; j < entitiesB.size(); j++){
				Entity oe = entitiesB.get(j);
				CBounds oeb = oe.getComponent(CBounds.class);
				CFrictionCause oef = oe.getComponent(CFrictionCause.class);
				if (ffo.frictionCoefficient.y > 0 && (Utils.checkEdgeContact(1, feb.bounds, oeb.bounds)
						|| Utils.checkEdgeContact(1, oeb.bounds, feb.bounds))) {
					Gdx.app.debug("Friction Detection", "Vertical Friction");
					fes.speed.y *= Math.pow(oef.frictionMultiplier.y, deltaTime) / ffo.frictionCoefficient.y;
				}
				if (ffo.frictionCoefficient.x > 0 && (Utils.checkEdgeContact(0, feb.bounds, oeb.bounds)
						|| Utils.checkEdgeContact(0, oeb.bounds, feb.bounds))){
					Gdx.app.debug("Friction Detection", "Horizontal Friction");
					fes.speed.x *= Math.pow(oef.frictionMultiplier.x, deltaTime) / ffo.frictionCoefficient.x;
				}
			}
		}

	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

}
