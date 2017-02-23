package code.elix_x.excore.client.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class WebResourcePack implements IResourcePack {

	public URL getURL(ResourceLocation location) throws IOException{
		return new URL(location.getResourcePath());
	}

	public boolean urlExists(URL url) throws IOException{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setInstanceFollowRedirects(false);
		return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
	}

	@Override
	public String getPackName(){
		return "WEB";
	}

	@Override
	public Set<String> getResourceDomains(){
		return Sets.newHashSet("http://www");
	}

	@Override
	public boolean resourceExists(ResourceLocation location){
		if(location.getResourceDomain().equals("http://www")){
			try{
				return urlExists(getURL(location));
			} catch(IOException e){

			}
		}
		return false;
	}

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException{
		return getURL(location).openStream();
	}

	@Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException{
		return null;
	}

	@Override
	public BufferedImage getPackImage() throws IOException{
		return null;
	}

}
