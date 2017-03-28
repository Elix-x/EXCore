package code.elix_x.excore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import code.elix_x.excore.utils.mod.IMod;
import code.elix_x.excore.utils.proxy.IProxy;
import net.minecraftforge.common.ForgeVersion;
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
	public static final String VERSION = "@VERSION@";

	public static final String DEPENDENCY = MODID + "@[" + VERSION + ",)";

	public static final String MCVERSION = ForgeVersion.mcVersion;
	public static final String MCVERSIONDEPENDENCY = "[" + MCVERSION + ",)";

	public static final boolean foolsTime = LocalDate.of(2017, 4, 1).isEqual(LocalDate.now());

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
		if(foolsTime)
			System.out.println(fromHex("0d 0a 0d 0a 0d 0a 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 60 27 2e 2d 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 20 20 20 20 20 20 20 20 2e 2d 27 2e 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 20 20 20 20 2d 2e 2f 5c 2e 2d 20 20 20 20 2e 2d 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2d 2e 20 20 2f 5f 7c 5c 20 20 2e 2d 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 20 20 20 60 2f 5f 5f 5f 5f 5c 27 20 20 20 2e 2d 27 2e 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 20 20 20 20 2d 2e 2f 2e 2d 22 22 2d 2e 5c 2e 2d 20 20 20 20 20 20 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 20 20 2f 3c 20 28 28 29 29 20 3e 5c 20 20 2e 2d 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2d 20 20 20 2e 60 2f 5f 5f 60 2d 2e 2e 2d 27 5f 5f 5c 27 20 20 20 2e 2d 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2c 2e 2e 2e 60 2d 2e 2f 5f 5f 5f 7c 5f 5f 5f 5f 7c 5f 5f 5f 5c 2e 2d 27 2e 2c 2e 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2c 2d 27 20 20 20 2c 60 20 2e 20 2e 20 27 2c 20 20 20 60 2d 2c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2c 2d 27 20 20 20 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 20 20 60 2d 2c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2c 27 2f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 5c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2f 20 2f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2f 20 2f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 27 20 2f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 5c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2e 27 20 2f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2c 27 20 2f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5c 0d 0a 2c 2c 2d 2d 2d 27 27 2d 2d 2e 2e 2e 5f 5f 5f 2e 2e 2e 2d 2d 27 27 27 2d 2d 2e 2e 20 2f 2e 2e 2f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 5c 20 2e 2e 2d 2d 60 60 60 2d 2d 2e 2e 2e 5f 5f 5f 2e 2e 2e 2d 2d 60 60 2d 2d 2d 2c 2c 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 27 2e 2e 2f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5c 0d 0a 20 20 20 20 20 20 5c 20 20 20 20 29 20 20 20 20 20 20 20 20 20 20 20 20 20 20 27 2e 3a 2f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5c 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 28 20 20 20 20 2f 0d 0a 20 20 20 20 20 20 29 5c 20 20 2f 20 29 20 20 20 20 20 20 20 20 20 20 20 2c 27 3a 2e 2f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 5c 20 20 20 20 20 20 20 20 20 20 20 20 20 28 20 5c 20 20 2f 28 0d 0a 20 20 20 20 20 2f 20 2f 20 28 20 28 20 20 20 20 20 20 20 20 20 20 20 2f 3a 2e 2e 2f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5c 20 20 20 20 20 20 20 20 20 20 20 20 20 29 20 29 20 5c 20 5c 0d 0a 20 20 20 20 7c 20 7c 20 20 20 5c 20 5c 20 20 20 20 20 20 20 20 20 2f 2e 2e 2e 2f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5c 20 20 20 20 20 20 20 20 20 20 20 2f 20 2f 20 20 20 7c 20 7c 0d 0a 20 2e 2d 2e 5c 20 5c 20 20 20 20 5c 20 5c 20 20 20 20 20 20 20 27 2e 2e 3a 2f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 7c 5f 5f 5f 5f 5f 5c 20 20 20 20 20 20 20 20 20 2f 20 2f 20 20 20 20 2f 20 2f 2e 2d 2e 0d 0a 28 3d 20 20 29 5c 20 60 2e 5f 2e 27 20 7c 20 20 20 20 20 20 20 5c 3a 2e 2f 20 5f 20 20 5f 20 5f 5f 5f 20 20 5f 5f 5f 5f 20 5f 5f 5f 5f 20 5f 20 20 20 20 5f 20 5f 20 5f 20 5f 20 5f 20 20 5f 20 5f 5f 5f 5c 20 20 20 20 20 20 20 20 7c 20 60 2e 5f 2e 27 20 2f 28 20 20 3d 29 0d 0a 20 5c 20 28 5f 29 20 20 20 20 20 20 20 29 20 20 20 20 20 20 20 5c 2e 2f 20 20 7c 5c 2f 7c 20 7c 5f 5f 29 20 7c 5f 5f 5f 20 7c 5f 5f 5f 20 7c 5f 5f 5f 20 5f 58 5f 20 5f 58 5f 20 20 5c 2f 20 20 5f 7c 5f 20 5c 20 20 20 20 20 20 20 28 20 20 20 20 20 20 20 28 5f 29 20 2f 0d 0a 20 20 5c 20 20 20 20 60 2d 2d 2d 2d 27 20 20 20 20 20 20 20 20 20 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 20 20 20 20 20 20 20 60 2d 2d 2d 2d 27 20 20 20 20 2f 0d 0a 20 20 20 5c 20 20 20 5f 5f 5f 5f 5c 5f 5f 20 20 20 20 20 20 20 20 20 20 5f 5f 20 5f 5f 20 20 20 20 5f 20 20 5f 5f 20 5f 20 20 20 20 20 5f 20 20 5f 5f 20 5f 5f 5f 5f 5f 5f 5f 5f 20 20 20 20 5f 5f 5f 5f 5f 20 20 20 20 20 20 20 20 5f 5f 2f 5f 5f 5f 5f 20 20 20 2f 0d 0a 20 20 20 20 5c 20 28 3d 5c 20 20 20 20 20 5c 20 20 20 20 20 20 20 20 28 5f 20 7c 5f 20 7c 56 7c 7c 5f 29 7c 5f 20 7c 5f 29 20 20 20 7c 5f 7c 28 5f 20 2f 20 20 20 7c 20 20 7c 20 20 20 20 7c 5f 20 20 7c 20 20 20 20 20 20 20 20 2f 20 20 20 20 20 2f 2d 29 20 2f 0d 0a 20 20 20 20 20 5c 5f 29 5f 5c 20 20 20 20 20 5c 20 20 20 20 20 20 20 5f 5f 29 7c 5f 5f 7c 20 7c 7c 20 20 7c 5f 5f 7c 20 5c 20 20 20 7c 20 7c 5f 5f 29 5c 5f 5f 5f 7c 5f 5f 7c 5f 20 20 20 7c 20 20 5f 7c 5f 20 20 20 20 20 20 2f 20 20 20 20 20 2f 5f 28 5f 2f 0d 0a 20 20 20 20 20 20 20 20 20 20 5c 20 20 20 20 20 5c 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 2f 20 20 20 20 20 2f 0d 0a 20 20 20 20 20 20 20 20 20 20 20 29 20 20 20 20 20 29 20 20 5f 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 20 20 28 20 20 20 20 20 28 0d 0a 20 20 20 20 20 20 20 20 20 20 28 20 20 20 20 20 28 2c 2d 27 20 60 2d 2e 2e 5f 5f 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 5f 2e 2e 2d 27 20 60 2d 2c 29 20 20 20 20 20 29 0d 0a 20 20 20 20 20 20 20 20 20 20 20 5c 5f 2e 2d 27 27 20 20 20 20 20 20 20 20 20 20 60 60 2d 2e 2e 5f 5f 5f 5f 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 5f 5f 5f 2e 2e 2d 27 27 20 20 20 20 20 20 20 20 20 20 60 60 2d 2e 5f 2f 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 5f 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 60 2d 2d 2e 2e 2e 5f 5f 5f 5f 2e 2e 2e 2d 2d 27 27 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 2e 2d 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 2e 5f 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 2e 2e 2d 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 2d 2e 2e 5f 5f 20 20 20 20 20 20 20 20 20 20 41 4c 54 2e 41 53 43 49 49 2d 41 52 54 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 5f 2e 2e 2d 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 60 2d 2e 2e 5f 5f 5f 5f 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 5f 5f 5f 5f 2e 2e 2d 27 27 0d 0a 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 60 60 2d 2d 2e 2e 2e 5f 5f 5f 5f 2e 2e 2e 2d 2d 27 27 0d 0a"));
		proxy.init(event);
	}

	private String fromHex(String hex){
		return new String(ArrayUtils.toPrimitive(Arrays.stream(hex.split(" ")).map(byt -> Byte.decode("0x" + byt)).collect(Collectors.toList()).toArray(new Byte[0])));
	}

	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		proxy.postInit(event);
	}

}
