package code.elix_x.excore;

import code.elix_x.excore.utils.mod.IMod;
import code.elix_x.excore.utils.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EXCore.MODID, name = EXCore.NAME, version = EXCore.VERSION, acceptedMinecraftVersions = EXCore.MCVERSIONDEPENDENCY)
public class EXCore implements IMod<EXCore, IProxy<EXCore>> {

	public static final String MODID = "excore";
	public static final String NAME = "EXCore";
	public static final String VERSION = "1.6.2";

	public static final String SPLIT_VERSION = "2.0";

	public static final String DEPENDENCY = MODID + "@[" + VERSION + "," + SPLIT_VERSION + ")";

	public static final String MCVERSION = "1.10.2";
	public static final String MCVERSIONDEPENDENCY = "[" + MCVERSION + ",)";

	@SidedProxy(modId = MODID, clientSide = "code.elix_x.excore.proxy.ClientProxy", serverSide = "code.elix_x.excore.proxy.ServerProxy")
	public static IProxy proxy;

	@Override
	public IProxy<EXCore> getProxy(){
		return proxy;
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		proxy.preInit(event);
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event){
		proxy.init(event);
	}

	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		proxy.postInit(event);
	}

}
