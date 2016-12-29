package code.elix_x.excore.utils.client.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public abstract class VertexBuffer {

	public static <B extends Buffer> B createBuffer(int vertexCount, VertexFormatElement element){
		switch(element.getType()){
			case BYTE:
				return (B) BufferUtils.createByteBuffer(element.getElementCount() * vertexCount);
			case UBYTE:
				return (B) BufferUtils.createByteBuffer(element.getElementCount() * vertexCount);
			case SHORT:
				return (B) BufferUtils.createShortBuffer(element.getElementCount() * vertexCount);
			case USHORT:
				return (B) BufferUtils.createShortBuffer(element.getElementCount() * vertexCount);
			case INT:
				return (B) BufferUtils.createIntBuffer(element.getElementCount() * vertexCount);
			case UINT:
				return (B) BufferUtils.createIntBuffer(element.getElementCount() * vertexCount);
			case FLOAT:
				return (B) BufferUtils.createFloatBuffer(element.getElementCount() * vertexCount);
			default:
				return (B) BufferUtils.createByteBuffer(element.getElementCount() * vertexCount);
		}
	}

	public static ByteBuffer createByteBuffer(int vertexCount, VertexFormatElement element){
		return BufferUtils.createByteBuffer(element.getSize() * vertexCount);
	}

	protected final VertexFormat format;
	protected final int drawMode;
	protected final int vertexCount;
	protected final VAO vao;

	protected VertexBuffer(VertexFormat format, int drawMode, int vertexCount){
		this.format = format;
		this.drawMode = drawMode;
		this.vertexCount = vertexCount;
		this.vao = new VAO();
	}

	public void draw(){
		renderPre();
		GL11.glDrawArrays(drawMode, 0, vertexCount);
		renderPost();
	}

	protected void renderPre(){
		vao.bind();
	}

	protected void renderPost(){
		vao.unbind();
	}

}
