package code.elix_x.excore.client.thingy;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import code.elix_x.excore.utils.color.RGBA;
import jline.internal.InputStreamReader;
import net.minecraft.util.text.ITextComponent;

public class ThingyData {

	private static final Gson gson = new GsonBuilder().registerTypeAdapter(ITextComponent.class, new ITextComponent.Serializer()).create();

	public static ThingyData read(URL url) throws IOException{
		return gson.fromJson(new InputStreamReader(url.openStream()), ThingyData.class);
	}

	List<Human> humans;
	Map<String, HumanCategory> humanCategories;
	Map<String, URL> linkIcons;

	@Override
	public String toString(){
		return "ThingyData [humans=" + humans + ", humanCategories=" + humanCategories + ", linkIcons=" + linkIcons + "]";
	}

	public class Human {

		String name;
		String category;
		List<ITextComponent> bio;
		URL icon;
		List<Link> links;

		HumanCategory getCategory(ThingyData data){
			return data.humanCategories.get(category);
		}

		@Override
		public String toString(){
			return "Human [name=" + name + ", category=" + category + ", bio=" + bio + ", icon=" + icon + ", links=" + links + "]";
		}

		public class Link {

			URL url;
			String icon;

			URL getIcon(ThingyData data){
				return data.linkIcons.get(icon);
			}

			@Override
			public String toString(){
				return "Link [url=" + url + ", icon=" + icon + "]";
			}

		}

	}

	public class HumanCategory {

		String name;
		boolean glint;
		RGBA color;

		@Override
		public String toString(){
			return "HumanCategory [name=" + name + ", glint=" + glint + ", color=" + color + "]";
		}

	}

}
