package code.elix_x.excore.proxy;

import java.util.List;

import code.elix_x.excomms.reflection.ReflectionHelper.AClass;
import code.elix_x.excore.EXCore;
import code.elix_x.excore.client.debug.AdvancedDebugTools;
import code.elix_x.excore.client.resource.WebResourcePack;
import code.elix_x.excore.client.thingy.Thingy;
import code.elix_x.excore.utils.proxy.IProxy;
import code.elix_x.excore.xmas.XMasSpecial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy<EXCore> {

	@Override
	public void preInit(FMLPreInitializationEvent event){
		((List<IResourcePack>) new AClass<Minecraft>(Minecraft.class).getDeclaredField("defaultResourcePacks", "field_110449_ao").setAccessible(true).get(Minecraft.getMinecraft())).add(new WebResourcePack());
		MinecraftForge.EVENT_BUS.register(new AdvancedDebugTools());
		MinecraftForge.EVENT_BUS.register(new XMasSpecial());
	}

	@Override
	public void init(FMLInitializationEvent event){
		new Thingy().start();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event){

	}

}
