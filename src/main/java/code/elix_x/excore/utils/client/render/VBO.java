package code.elix_x.excore.utils.client.render;

import org.lwjgl.opengl.GL15;

public class VBO {

	private final int vboId;

	public VBO(){
		this.vboId = GL15.glGenBuffers();
	}

	public void bind(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
	}

	public void unbind(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void cleanUp(){
		unbind();
		GL15.glDeleteBuffers(vboId);
	}

}
