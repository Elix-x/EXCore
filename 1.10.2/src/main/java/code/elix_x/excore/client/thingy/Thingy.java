package code.elix_x.excore.client.thingy;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import net.minecraftforge.common.MinecraftForge;

public class Thingy extends Thread {

	private ThingyData data;

	private ThingyDisplay display;

	public Thingy(){
		super("Excore Thingy Display");
	}

	@Override
	public void run(){
		try{
			URL url = new URL("https://gist.githubusercontent.com/elix-x/be82bef4a24490c404d69e5bf9c24896/raw/thingy.json");
			data = ThingyData.read(url);
		} catch(IOException e){
			e.printStackTrace();
			return;
		}
		display = new ThingyDisplay(this, data, new Random("Elix_x".hashCode()), 250);
		MinecraftForge.EVENT_BUS.register(display);
	}

}
