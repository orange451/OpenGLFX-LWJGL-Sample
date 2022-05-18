package engine.lua.type.data;

import engine.lua.type.LuaValuetype;
import io.jadefx.paint.Color;

public abstract class ColorBase extends LuaValuetype {
	public abstract Color toColor();
}
