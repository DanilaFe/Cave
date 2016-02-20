package com.danilafe.cave.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.danilafe.cave.CaveGame;
import com.danilafe.cave.Utils;
import com.danilafe.cave.attacks.WeaponProperties;
import com.danilafe.cave.ecs.components.CBounds;
import com.danilafe.cave.ecs.components.CDamageCause;
import com.danilafe.cave.ecs.components.CDisabled;
import com.danilafe.cave.ecs.components.CDisappearing;
import com.danilafe.cave.ecs.components.CFollow;
import com.danilafe.cave.ecs.components.CWeapon;
import com.danilafe.cave.ecs.components.CWeaponTriggerable;
import com.danilafe.cave.ecs.components.CWeaponWielding;

/**
 * Updates weapon entities based on their parameters
 * @author vanilla
 *
 */
public class WeaponSystem extends FamilySystem {

	/**
	 * Creates a new WeaponSystem
	 */
	public WeaponSystem() {
		super(Family.all(CWeapon.class, CFollow.class, CBounds.class, CDamageCause.class).exclude(CDisabled.class).get(), Family.all(CWeaponWielding.class, CWeaponTriggerable.class).exclude(CDisabled.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for(int i = 0; i < entitiesA.size(); i ++){
			Entity entity = entitiesA.get(i);
			CWeapon weapon = entity.getComponent(CWeapon.class);
			CFollow weaponFollowing = entity.getComponent(CFollow.class);
			CDamageCause weaponDamageCause = entity.getComponent(CDamageCause.class);
			CBounds weaponBounds = entity.getComponent(CBounds.class);

			weapon.weapon.remainingDuration += deltaTime;
			WeaponProperties generatedProperties = weapon.weapon.currentChain.attackType.calculateProperties(weapon.weapon, weaponFollowing.following, entity);
			weaponBounds.bounds.setSize(generatedProperties.wSize.x, generatedProperties.wSize.y);
			weaponFollowing.offset.set(generatedProperties.wOffset);
			weaponDamageCause.damage = generatedProperties.wDamage;
			weaponDamageCause.maxDelay = generatedProperties.wDelay;
			weaponDamageCause.additionalKnockback.set(generatedProperties.wAdditionalKnockback);
			weaponDamageCause.knockback = generatedProperties.wKnockback;
		}
		for(int i = 0; i < entitiesB.size(); i++){
			Entity entity = entitiesB.get(i);
			CWeaponWielding weaponWielding = entity.getComponent(CWeaponWielding.class);
			CWeaponTriggerable weaponTriggerable = entity.getComponent(CWeaponTriggerable.class);
			if(Gdx.input.isKeyJustPressed(weaponTriggerable.attackKeycode)) {
				if(weaponWielding.weaponEntity == null) {
					CaveGame.instance.pooledEngine.addEntity((weaponWielding.weaponEntity = Utils.createWeaponEntity(entity, weaponWielding.weaponData)));
				}
				else if(weaponWielding.weaponData != null){
					Entity weaponEntity = weaponWielding.weaponEntity;
					CWeapon weapon = weaponEntity.getComponent(CWeapon.class);
					if(weapon.weapon.remainingDuration >= weapon.weapon.currentChain.comboWindowMin &&
							weapon.weapon.remainingDuration <= weapon.weapon.currentChain.comboWindowMax) {
						boolean hasTriggered = false;
						for(Integer integer : weapon.weapon.currentChain.comboChain.keySet()){
							if(Gdx.input.isKeyPressed(integer)) {
								weapon.weapon.currentChain = weapon.weapon.currentChain.comboChain.get(integer);
								weapon.weapon.remainingDuration = 0;
								weaponEntity.getComponent(CDisappearing.class).remaingTime = weapon.weapon.currentChain.duration;
								if(weapon.weapon.currentChain.onTrigger != null) weapon.weapon.currentChain.onTrigger.update(entity, deltaTime);
								hasTriggered = true;
								break;
							}
						}
						if(!hasTriggered && weapon.weapon.currentChain.comboChain.containsKey(0)) {
							weapon.weapon.currentChain = weapon.weapon.currentChain.comboChain.get(0);
							weapon.weapon.remainingDuration = 0;
							weaponEntity.getComponent(CDisappearing.class).remaingTime = weapon.weapon.currentChain.duration;
							if(weapon.weapon.currentChain.onTrigger != null) weapon.weapon.currentChain.onTrigger.update(entity, deltaTime);
							hasTriggered = true;
						}
					}
				}
			}
		}

	}

}
