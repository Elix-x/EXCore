package code.elix_x.excore.client.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import code.elix_x.excore.utils.reflection.AdvancedReflectionHelper.AField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class AdvancedDebugTools {

	private static Map<Integer, DebugTool> debugTools = new HashMap<>();
	private static Map<Integer, DebugTool> guiDebugTools = new HashMap<>();

	public static void register(int key, DebugTool tool){
		assert !debugTools.containsKey(key) : "Tool already registered for key " + key + "!";
		debugTools.put(key, tool);
	}

	public static void registerGUI(int key, DebugTool tool){
		assert !guiDebugTools.containsKey(key) : "Tool already registered for key " + key + "!";
		guiDebugTools.put(key, tool);
	}

	static{
		register(Keyboard.KEY_P, new VanillaDebugTool("Path Finding", "field_190080_f"));
		register(Keyboard.KEY_W, new VanillaDebugTool("Water", "field_190081_g"));
		register(Keyboard.KEY_H, new VanillaDebugTool("Height Map", "field_190082_h"));
	}

	@SubscribeEvent
	public void keyPressed(KeyInputEvent event){
		if(Keyboard.getEventKey() == Keyboard.KEY_F3 && GuiScreen.isAltKeyDown()){
			for(Entry<Integer, DebugTool> e : debugTools.entrySet()){
				if(Keyboard.isKeyDown(e.getKey())) e.getValue().toggle();
			}
		}
	}

	@SubscribeEvent
	public void guiKeyPressed(GuiScreenEvent.KeyboardInputEvent event){
		if(Keyboard.getEventKey() == Keyboard.KEY_F3 && GuiScreen.isAltKeyDown()){
			for(Entry<Integer, DebugTool> e : guiDebugTools.entrySet()){
				if(Keyboard.isKeyDown(e.getKey())) e.getValue().toggle();
			}
		}
	}

	public static interface DebugTool {

		public void toggle();

	}

	public static class VanillaDebugTool implements DebugTool {

		private final String name;
		private final AField<DebugRenderer, Boolean> field;

		public VanillaDebugTool(String name, String... names){
			this.name = name;
			this.field = new AField(DebugRenderer.class, names).setAccessible(true);
		}

		@Override
		public void toggle(){
			boolean b;
			field.set(Minecraft.getMinecraft().debugRenderer, b = !field.get(Minecraft.getMinecraft().debugRenderer));
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((new TextComponentString("")).appendSibling((new TextComponentString("[Advanced Debug]: ")).setStyle((new Style()).setColor(TextFormatting.GOLD).setBold(Boolean.valueOf(true)))).appendText(String.format("%s: %s", this.name, (b ? "shown" : "hidden"))));
		}

	}

}