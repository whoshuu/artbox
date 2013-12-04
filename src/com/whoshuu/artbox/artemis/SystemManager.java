package com.whoshuu.artbox.artemis;

import java.util.HashMap;
import java.util.Map;

import com.whoshuu.artbox.artemis.utils.Bag;

/**
 * If you need to communicate with systems from other system, then look it up here.
 * Use the world instance to retrieve a instance.
 * 
 * @author Arni Arent
 *
 */
public class SystemManager {
	private GameWorld world;
	private Map<Class<?>, EntitySystem> systems;
	private Bag<EntitySystem> bagged;
	
	public SystemManager(GameWorld world) {
		this.world = world;
		systems = new HashMap<Class<?>, EntitySystem>();
		bagged = new Bag<EntitySystem>();
	}
	
	public EntitySystem setSystem(EntitySystem system) {
		system.setWorld(world);
		
		systems.put(system.getClass(), system);
		
		if(!bagged.contains(system))
			bagged.add(system);
		
		system.setSystemBit(SystemBitManager.getBitFor(system.getClass()));
		
		return system;
	}
	
	public <T extends EntitySystem> T getSystem(Class<T> type) {
		return type.cast(systems.get(type));
	}
	
	public Bag<EntitySystem> getSystems() {
		return bagged;
	}
	
	/**
	 * After adding all systems to the world, you must initialize them all.
	 */
	public void initializeAll() {
	   for (int i = 0; i < bagged.size(); i++) {
	      bagged.get(i).initialize();
	   }
	} 

}
