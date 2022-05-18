/*
 *
 * Copyright (C) 2015-2020 Anarchy Engine Open Source Contributors (see CONTRIBUTORS.md)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package engine.lua.type.object.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import engine.lua.LuaEngine;
import engine.lua.type.LuaEvent;
import engine.lua.type.object.Instance;
import engine.lua.type.object.Service;

public class Game extends Instance {
	public Map<UUID,Instance> uniqueInstances = new HashMap<>();
	
	private Map<String, Service> services = new HashMap<>();

	private static final LuaValue C_LOADED = LuaValue.valueOf("Loaded");
	private static final LuaValue C_STARTED = LuaValue.valueOf("Started");
	private static final LuaValue C_STOPPING = LuaValue.valueOf("Stopping");
	private static final LuaValue C_RESETEVENT = LuaValue.valueOf("ResetEvent");
	private static final LuaValue C_SELECTIONCHANGED = LuaValue.valueOf("SelectionChanged");
	
	private static final LuaValue C_RUNNING = LuaValue.valueOf("Running");
	private static final LuaValue C_ISSERVER = LuaValue.valueOf("IsServer");

	private static final LuaValue C_SERVICE_WORKSPACE = LuaValue.valueOf("Workspace");
	private static final LuaValue C_SERVICE_RUNSERVICE = LuaValue.valueOf("RunService");
	private static final LuaValue C_SERVICE_DEBRIS = LuaValue.valueOf("Debris");
	
	private boolean started = false;
	
	public Game() {
		super("Game");
		
		// On load event
		this.rawset(C_LOADED, new LuaEvent());
		this.rawset(C_STARTED, new LuaEvent());
		this.rawset(C_STOPPING, new LuaEvent());
		this.rawset(C_RESETEVENT, new LuaEvent());
		this.rawset(C_SELECTIONCHANGED, new LuaEvent());
		
		// Fields
		this.defineField(C_RUNNING, LuaValue.valueOf(false), true);
		this.defineField(C_ISSERVER, LuaValue.valueOf(false), true);
		
		// GetService convenience method
		getmetatable().set("GetService", new TwoArgFunction() {
			@Override
			public LuaValue call(LuaValue arg, LuaValue arg2) {
				Service service = getService(arg2.toString());
				if ( service == null )
					return LuaValue.NIL;
				return service;
			}
		});
		
		this.descendantRemovedEvent().connectLua(new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue object) {
					if ( object instanceof Instance ) {
						Instance inst = (Instance) object;
						UUID UUID = inst.getUUID();
						if ( UUID == null )
							return LuaValue.NIL;
						
						// Remove unique reference
						uniqueInstances.remove(UUID);
					}
				return LuaValue.NIL;
			}
		});
		
		this.descendantAddedEvent().connectLua(new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue object) {
				if ( object instanceof Instance ) {
					Instance newInst = (Instance)object;
					
					// Generate UUID
					if ( newInst.getUUID() == null ) {
						UUID tuid = generateUUID();
						newInst.setUUID(tuid);
						uniqueInstances.put(tuid, newInst);
					} else {
						Instance oldInst = uniqueInstances.get(newInst.getUUID());
						if ( oldInst == null ) {
							uniqueInstances.put(newInst.getUUID(),newInst);
						} else if ( oldInst != newInst ) {
							UUID tuid = generateUUID();
							newInst.setUUID(tuid);
							uniqueInstances.put(tuid, newInst);
						}
					}
				}
				return LuaValue.NIL;
			}
		});
		
		LuaEngine.globals.set("game", this);
		services();
		
		this.getStartedEvent().connect((args)->{
			started = true;
		});
		
		// LOCK HER UP
		setLocked(true);
		setInstanceable(false);
	}
	
	public void start() {
		if ( started )
			return;
		
		this.getStartedEvent().fire();
	}
	
	public boolean isStarted() {
		return this.started;
	}
	
	public LuaEvent getStartedEvent() {
		return (LuaEvent)this.get(C_STARTED);
	}
	
	public Workspace workspace() {
		return (Workspace)this.get(C_SERVICE_WORKSPACE);
	}
	
	public RunService runService() {
		return (RunService)this.get(C_SERVICE_RUNSERVICE);
	}
	
	public Debris debris() {
		return (Debris)this.get(C_SERVICE_DEBRIS);
	}
	
	private void services() {
		addService(Workspace.class);
		addService(Debris.class);
		addService(RunService.class);
	}
	
	private void addService(Class<? extends Service> clazz) {
		try {
			@SuppressWarnings("deprecation")
			Service service = clazz.newInstance();
			services.put(service.getName(), service);
			service.forceSetParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Service getService(String serviceName) {
		return services.get(serviceName);
	}

	protected UUID generateUUID() {
		while(true) {
			UUID temp = UUID.randomUUID();
			if ( !uniqueInstances.containsKey(temp) ) {
				return uuid;
			}
		}
	}

	public Map<UUID, Instance> getInstanceMap() {
		Map<UUID, Instance> map = new HashMap<>(this.uniqueInstances);
		return map;
	}
	
	public String getName() {
		return this.get(C_NAME).toString().toLowerCase();
	}

	@Override
	public void onDestroy() {
		//
	}

	@Override
	protected LuaValue onValueSet(LuaValue key, LuaValue value) {
		return null;
	}

	@Override
	protected boolean onValueGet(LuaValue key) {
		return true;
	}
}