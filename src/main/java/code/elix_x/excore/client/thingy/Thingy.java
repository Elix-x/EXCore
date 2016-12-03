package code.elix_x.excore.client.thingy;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Thingy extends Thread {

	public Thingy(){
		super("Excore Thingy Display");
	}

	@Override
	public void run(){
		final ThingyData data;
		try{
			URL url = new URL("https://gist.githubusercontent.com/elix-x/be82bef4a24490c404d69e5bf9c24896/raw/thingy.json");
			data = ThingyData.read(url);
		} catch(IOException e){
			e.printStackTrace();
			return;
		}
		final ThingyDisplay display = new ThingyDisplay(data, new Random(Minecraft.getMinecraft().getSession().getUsername().hashCode()), 5000);
		MinecraftForge.EVENT_BUS.register(display);

		Minecraft.getMinecraft().addScheduledTask(new Runnable(){

			@Override
			public void run(){
				display.cacheIcons();
			}

		});
	}

}
