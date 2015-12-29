package com.danilafe.cave.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.danilafe.cave.animation.AnimationQueue;

/**
 * Animation component. A component that wraps and animation queue, updating it and its current animation when
 * it updates.
 * @author vanilla
 *
 */
public class CAnimation implements Poolable, Component {

	/**
	 * The animation queue, used for scheduling animations.
	 */
	public AnimationQueue animationQueue = new AnimationQueue();
	/**
	 * The rotation of this animation
	 */
	public int rotation = 0;

	@Override
	public void reset() {
		animationQueue.deltaTime = -1;
		animationQueue.animationQueue.clear();
		rotation = 0;
	}

}
