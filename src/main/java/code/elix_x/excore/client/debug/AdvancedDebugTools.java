package code.elix_x.excore.client.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import code.elix_x.excomms.reflection.ReflectionHelper.AClass;
import code.elix_x.excomms.reflection.ReflectionHelper.AField;
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
		MinecraftForge.EVENT_BUS.register(AdvancedDebugTools.class);

		register(Keyboard.KEY_P, new VanillaDebugTool("Path Finding", "pathfindingEnabled", "field_190080_f"));
		register(Keyboard.KEY_W, new VanillaDebugTool("Water", "waterEnabled", "field_190081_g"));
		register(Keyboard.KEY_J, new VanillaDebugTool("Height Map", "heightMapEnabled", "field_190082_h"));
		register(Keyboard.KEY_V, new VanillaDebugTool("Collision Box", "collisionBoxEnabled", "field_191326_j"));
		register(Keyboard.KEY_M, new VanillaDebugTool("Neighbors Update", "neighborsUpdateEnabled", "field_191558_l"));
		register(Keyboard.KEY_Z, new VanillaDebugTool("Solid Face", "solidFaceEnabled", "field_193853_n"));
	}

	public static void clinit(){

	}

	@SubscribeEvent
	public static void keyPressed(KeyInputEvent event){
		if(Keyboard.getEventKey() == Keyboard.KEY_F3 && GuiScreen.isAltKeyDown()){
			for(Entry<Integer, DebugTool> e : debugTools.entrySet()){
				if(Keyboard.isKeyDown(e.getKey())) e.getValue().toggle();
			}
		}
	}

	@SubscribeEvent
	public static void guiKeyPressed(GuiScreenEvent.KeyboardInputEvent event){
		if(Keyboard.getEventKey() == Keyboard.KEY_F3 && GuiScreen.isAltKeyDown()){
			for(Entry<Integer, DebugTool> e : guiDebugTools.entrySet()){
				if(Keyboard.isKeyDown(e.getKey())) e.getValue().toggle();
			}
		}
	}

	public interface DebugTool {

		void toggle();

	}

	public static class VanillaDebugTool implements DebugTool {

		private final String name;
		private final AField<DebugRenderer, Boolean> field;

		public VanillaDebugTool(String name, String... names){
			this.name = name;
			this.field = new AClass<>(DebugRenderer.class).<Boolean>getDeclaredField(names).orElseThrow(() -> new IllegalArgumentException(String.format("Failed to initialize vanilla debug tool for %s using following field names: %s", name, names))).setAccessible(true);
		}

		@Override
		public void toggle(){
			boolean b;
			field.set(Minecraft.getMinecraft().debugRenderer, b = !field.get(Minecraft.getMinecraft().debugRenderer).get());
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((new TextComponentString("")).appendSibling((new TextComponentString("[Advanced Debug]: ")).setStyle((new Style()).setColor(TextFormatting.GOLD).setBold(true))).appendText(String.format("%s: %s", this.name, (b ? "shown" : "hidden"))));
		}

	}

}