package code.elix_x.excore.utils.client.render;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUsage;

public class VAO {

	private final int vaoId;

	public VAO(){
		this.vaoId = GL30.glGenVertexArrays();
	}

	public void bind(){
		GL30.glBindVertexArray(vaoId);
	}

	public void vboSeparate(VBO vbo, VertexFormatElement format){
		GL20.glVertexAttribPointer(0, format.getElementCount(), format.getType().getGlConstant(), format.getUsage() == EnumUsage.NORMAL, 0, 0);
	}

	public void vboSingle(VBO vbo, VertexFormat format, VertexFormatElement elementFormat){
		GL20.glVertexAttribPointer(0, elementFormat.getElementCount(), elementFormat.getType().getGlConstant(), elementFormat.getUsage() == EnumUsage.NORMAL, format.getIntegerSize(), format.getOffset(elementFormat.getIndex()));
	}

	public void unbind(){
		GL30.glBindVertexArray(0);
	}

	public void cleanUp(){
		unbind();
		GL30.glDeleteVertexArrays(vaoId);
	}

}
