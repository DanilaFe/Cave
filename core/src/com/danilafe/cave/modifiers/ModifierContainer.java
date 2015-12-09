package com.danilafe.cave.modifiers;

import java.util.LinkedList;

public class ModifierContainer {

	public LinkedList<Modifier> modifierList = new LinkedList<Modifier>();
	
	public void removeAll(Modifier.ModifierType type){
		for(int i = modifierList.size() - 1; i > 0; i --){
			modifierList.remove(i);
		}
	}
}
