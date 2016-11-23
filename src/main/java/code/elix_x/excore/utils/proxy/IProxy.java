package code.elix_x.excore.utils.proxy;

import code.elix_x.excore.utils.mod.IMod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy<M extends IMod<M, ? extends IProxy<M>>> {

	public void preInit(FMLPreInitializationEvent event);

	public void init(FMLInitializationEvent event);

	public void postInit(FMLPostInitializationEvent event);

}
