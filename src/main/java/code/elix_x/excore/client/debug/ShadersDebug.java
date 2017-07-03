package code.elix_x.excore.client.debug;

import code.elix_x.excomms.reflection.ReflectionHelper.AClass;
import code.elix_x.excomms.reflection.ReflectionHelper.AField;
import code.elix_x.excore.client.debug.AdvancedDebugTools.DebugTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ShadersDebug implements DebugTool {

	private static final ResourceLocation[] SHADERS_TEXTURES = new AClass<>(EntityRenderer.class).<ResourceLocation[]> getDeclaredField("SHADERS_TEXTURES", "field_147712_ad").setAccessible(true).get(null);
	private static final AField<EntityRenderer, Integer> shaderIndex = new AClass<>(EntityRenderer.class).<Integer> getDeclaredField("shaderIndex", "field_147713_ae").setAccessible(true);

	@Override
	public void toggle(){
		EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
		int shader = shaderIndex.get(renderer);
		shader = (shader + 1) % (SHADERS_TEXTURES.length + 1);
		shaderIndex.set(renderer, shader);
		String sname;
		if(shader == SHADERS_TEXTURES.length){
			renderer.loadEntityShader(Minecraft.getMinecraft().getRenderViewEntity());
			sname = "OFF";
		} else{
			renderer.loadShader(SHADERS_TEXTURES[shader]);
			sname = SHADERS_TEXTURES[shader].toString();
		}
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((new TextComponentString("")).appendSibling((new TextComponentString("[Advanced Debug]: ")).setStyle((new Style()).setColor(TextFormatting.GOLD).setBold(true))).appendText(String.format("Shader: %s", sname)));
	}

}
