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

import java.util.List;

import org.luaj.vm2.LuaValue;

import engine.lua.type.object.Instance;
import engine.lua.type.object.ScriptExecutor;
import engine.lua.type.object.Service;
import engine.lua.type.object.TreeViewable;
import ide.layout.windows.icons.Icons;

public class Workspace extends Service implements TreeViewable,ScriptExecutor  {
	private static final LuaValue C_GRAVITY = LuaValue.valueOf("Gravity");
	private static final LuaValue C_CURRENTCAMERA = LuaValue.valueOf("CurrentCamera");

	public Workspace() {
		super("Workspace");

		this.defineField(C_CURRENTCAMERA, LuaValue.NIL, false);
		this.defineField(C_GRAVITY, LuaValue.valueOf(16), false);
	}

	public void tick() {
		List<Instance> descendants = this.getDescendants();
		
		synchronized(descendants) {
			for (int i = 0; i < descendants.size(); i++) {
				if ( i >= descendants.size() )
					continue;
				
				Instance d = descendants.get(i);
				if ( d == null )
					continue;

				d.internalTick();
			}
		}
	}

	@Override
	protected LuaValue onValueSet(LuaValue key, LuaValue value) {
		if ( key.eq_b(C_CURRENTCAMERA) ) {
			//if ( value == null || (!value.isnil() && !(value instanceof Camera)) )
				//return null;
			
			if ( ((Instance)value).getParent().isnil() )
				((Instance)value).forceSetParent(this);
		}
		return value;
	}

	@Override
	protected boolean onValueGet(LuaValue key) {
		return true;
	}
	
	@Override
	public Icons getIcon() {
		return Icons.icon_workspace;
	}

	/**
	 * Returns the current gravity in the workspace.
	 * @return
	 */
	public float getGravity() {
		return this.get(C_GRAVITY).tofloat();
	}
	
	/**
	 * Sets the current gravity in the workspace
	 */
	public void setGravity(float gravity) {
		this.set(C_GRAVITY, LuaValue.valueOf(gravity));
	}
}
