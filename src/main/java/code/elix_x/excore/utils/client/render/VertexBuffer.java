package code.elix_x.excore.utils.client.render;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class VertexBuffer {

	private static VertexFormat removePaddings(VertexFormat format){
		VertexFormat nf = new VertexFormat();
		for(VertexFormatElement element : format.getElements()){
			if(element.getUsage() != VertexFormatElement.EnumUsage.PADDING) nf.addElement(element);
		}
		return nf;
	}

	private final VertexFormat format;
	private final int drawMode;
	private final int vertexCount;
	protected final VAO vao;
	private final VBO[] vbos;

	private VertexBuffer(VertexFormat format, int drawMode, int vertexCount){
		this.format = removePaddings(format);
		this.drawMode = drawMode;
		this.vertexCount = vertexCount;
		this.vao = new VAO();
		this.vbos = new VBO[this.format.getElementCount()];
		for(int i = 0; i < this.vbos.length; i++){
			this.vbos[i] = new VBO();
		}
	}

	public VertexBuffer(net.minecraft.client.renderer.VertexBuffer buffer){
		this(buffer.getVertexFormat(), buffer.getDrawMode(), buffer.getVertexCount());
		ByteBuffer bytebuf = buffer.getByteBuffer();
		ByteBuffer[] byteBuffers = new ByteBuffer[vbos.length];
		for(int v = 0; v < vertexCount; v++){

		}
	}

	public void draw(){
		vao.bind();
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(drawMode, 0, vertexCount);
		GL20.glDisableVertexAttribArray(0);
		vao.unbind();
	}

}
