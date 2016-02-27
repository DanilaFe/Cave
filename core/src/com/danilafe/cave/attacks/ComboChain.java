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
	 * The lower bound of the error room during which this attack can be triggered
	 */
	public float comboWindowMin = 0;
	/**
	 * The upper bound of the error room during which this attack can be triggered
	 */
	public float comboWindowMax = 0;
	/**
	 * The duration of this attack
	 */
	public float duration = 0;
	/**
	 * Whether no other controls should be allowed during this attack's duration
	 */
	public boolean lockInput = false;

}
