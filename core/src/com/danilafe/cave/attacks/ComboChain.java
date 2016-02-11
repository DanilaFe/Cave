package com.danilafe.cave.attacks;

import java.util.HashMap;

import com.danilafe.cave.runnable.ECSRunnable;

/**
 * Describes a combo chain for attacks
 * @author vanilla
 *
 */
public class ComboChain {


	/**
	 * Attack in the sequence after this now.
	 */
	public HashMap<Integer, ComboChain> comboChain = new HashMap<Integer, ComboChain>();

	/**
	 * The calculator for the attack
	 */
	public WeaponPropertiesCalculator attackType;
	/**
	 * Execute when this attack is triggered
	 */
	public ECSRunnable onTrigger;
	/**
	 * Room for error between combos
	 */
	public float comboWindowMin = 0, comboWindowMax = 0;

}
