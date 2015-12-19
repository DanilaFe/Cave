package com.danilafe.cave.animation;

import java.util.LinkedList;

/**
 * A Queue for animations - more animations can be added to be performed
 * in order after the previous one completes.
 * @author vanilla
 *
 */
public class AnimationQueue {

	/**
	 * List of all the animations scheduled.
	 */
	public LinkedList<Animation> animationQueue = new LinkedList<Animation>();
	/**
	 * Milliseconds left before next frame
	 */
	public float deltaTime = 0;

	/**
	 * Updates the animation queue. This will trigger frame updates in the animations, and, if necessary,
	 * switch to the next animation.
	 * @param deltaTime the delta time to use in this update.
	 */
	public void update(float deltaTime){
		this.deltaTime -= deltaTime;
		while(this.deltaTime <= 0 && animationQueue.getFirst().animationParameter.frameDelta != 0) {
			if(animationQueue.size() <= 0) return;
			Animation currentAnimation = animationQueue.getFirst();
			this.deltaTime += currentAnimation.animationParameter.frameDelta;
			currentAnimation.texIndex++;
			if(currentAnimation.texIndex == currentAnimation.animationParameter.textures.length * currentAnimation.animationParameter.textures[0].length){
				if(currentAnimation.animationParameter.loop) currentAnimation.texIndex = 0;
				else animationQueue.pop();
			}
		}
	}

	/**
	 * Convenience method to schedule an animation.
	 * @param animation the animation to schedule.
	 */
	public void add(Animation animation){
		animationQueue.add(animation);
	}
}
