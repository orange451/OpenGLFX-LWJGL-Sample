package dev.anarchy;

import engine.lua.LuaEngine;
import engine.lua.type.object.insts.script.Script;
import engine.lua.type.object.services.Game;

public class ECSTest {
	/**
	 * JavaFX Hack to run JavaFX applications within IDE or CLI
	 */
	public static void main(String[] args) {
		LuaEngine.initialize();
		
		Game game = new Game();
		game.start();
		
		Script script = new Script();
		script.setSource("print(script:GetFullName())");
		script.setParent(game.workspace());
	}
}
