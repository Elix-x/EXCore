package code.elix_x.excore.utils.client.cursor;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import com.google.common.base.Throwables;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CursorHelper {

	public static final Cursor defaultCursor = Mouse.getNativeCursor();

	public static Cursor createCursor(ResourceLocation texture){
		try{
			BufferedImage image = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(texture).getInputStream());
			return new Cursor(image.getWidth(), image.getHeight(), image.getWidth() / 2, image.getHeight() / 2, 1, IntBuffer.wrap(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth())), null);
		} catch(Exception e){
			throw Throwables.propagate(e);
		}
	}

	public static Cursor createCursor(ResourceLocation texture, int hotSpotX, int hotSpotY){
		try{
			BufferedImage image = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(texture).getInputStream());
			return new Cursor(image.getWidth(), image.getHeight(), hotSpotX, hotSpotY, 1, IntBuffer.wrap(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth())), null);
		} catch(Exception e){
			throw Throwables.propagate(e);
		}
	}

	public static void setCursor(Cursor cursor){
		try{
			Mouse.setNativeCursor(cursor);
		} catch(LWJGLException e){
			Throwables.propagate(e);
		}
	}

	public static void resetCursor(){
		setCursor(defaultCursor);
	}

}
