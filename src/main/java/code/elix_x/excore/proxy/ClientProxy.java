package code.elix_x.excore.proxy;

import code.elix_x.excore.EXCore;
import code.elix_x.excore.client.debug.AdvancedDebugTools;
import code.elix_x.excore.client.debug.ShadersDebug;
import code.elix_x.excore.client.thingy.Thingy;
import code.elix_x.excore.utils.proxy.IProxy;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy implements IProxy<EXCore> {

	@Override
	public void preInit(FMLPreInitializationEvent event){
		AdvancedDebugTools.clinit();
	}

	@Override
	public void init(FMLInitializationEvent event){
		new Thingy().start();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event){
		if(OpenGlHelper.shadersSupported) AdvancedDebugTools.register(Keyboard.KEY_K, new ShadersDebug());
	}

}
