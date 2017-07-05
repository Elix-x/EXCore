package code.elix_x.excore.utils.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;

public class FrameBufferSnapshot {

	private final int snapshot;

	public FrameBufferSnapshot(){
		this.snapshot = OpenGlHelper.glGenFramebuffers();
	}

	public void readFrom(int source, int mask){
		GL45.glBlitNamedFramebuffer(source, snapshot, -1, -1, 1, 1, -1, -1, 1, 1, mask, GL11.GL_NEAREST);
	}

	public void readFrom(int mask){
		readFrom(Minecraft.getMinecraft().getFramebuffer().framebufferObject, mask);
	}

	public void writeTo(int destination, int mask){
		GL45.glBlitNamedFramebuffer(snapshot, destination, -1, -1, 1, 1, -1, -1, 1, 1, mask, GL11.GL_NEAREST);
	}

	public void writeTo(int mask){
		writeTo(Minecraft.getMinecraft().getFramebuffer().framebufferObject, mask);
	}

	public void delete(){
		OpenGlHelper.glDeleteFramebuffers(snapshot);
	}

}
