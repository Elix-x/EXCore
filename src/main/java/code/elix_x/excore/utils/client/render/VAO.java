package code.elix_x.excore.utils.client.render;

import org.lwjgl.opengl.GL30;

import net.minecraft.client.renderer.GlStateManager;

public class VAO {

	private final int vaoId;
	
	public VAO(){
		this.vaoId = GL30.glGenVertexArrays();
	}
	
	public void bind(){
		GL30.glBindVertexArray(vaoId);
	}
	
	public void unbind(){
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp(){
		unbind();
		GL30.glDeleteVertexArrays(vaoId);
	}
	
}
