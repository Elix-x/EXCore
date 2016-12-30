package code.elix_x.excore.utils.client.render.vbo;

import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import code.elix_x.excore.utils.client.render.IVertexBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.fml.common.FMLLog;

public class VertexBufferSingleVBO implements IVertexBuffer {

	protected final VertexFormat format;
	protected final int drawMode;
	protected final int vertexCount;
//	private final VBO vbo;
	private final VertexBuffer vbo;

	private VertexBufferSingleVBO(VertexFormat format, int drawMode, int vertexCount){
		this.format = format;
		this.drawMode = drawMode;
		this.vertexCount = vertexCount;
//		this.vbo = new VBO();
		this.vbo = new VertexBuffer(format);
	}

	public VertexBufferSingleVBO(net.minecraft.client.renderer.VertexBuffer vertexBuffer){
		this(vertexBuffer.getVertexFormat(), vertexBuffer.getDrawMode(), vertexBuffer.getVertexCount());
		ByteBuffer buffer = vertexBuffer.getByteBuffer();
//		vbo.bind();
//		vbo.data(buffer, GL15.GL_STATIC_DRAW);
//		vbo.unbind();
		vbo.bufferData(buffer);
	}

	protected void renderPre(){
//		vbo.bind();
		vbo.bindBuffer();
		boolean b = false;
		if(b){
			GlStateManager.glEnableClientState(32884);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.glEnableClientState(32886);
            
			GlStateManager.glVertexPointer(3, 5126, 28, 0);
	        GlStateManager.glColorPointer(4, 5121, 28, 12);
	        GlStateManager.glTexCoordPointer(2, 5126, 28, 16);
	        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.glTexCoordPointer(2, 5122, 28, 24);
	        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
		} else for(int i = 0; i < format.getElementCount(); i++){
			VertexFormatElement element = format.getElement(i);
			switch(element.getUsage()){
				case POSITION:
					GL11.glVertexPointer(element.getElementCount(), element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					glEnableClientState(GL_VERTEX_ARRAY);
					break;
				case NORMAL:
					GL11.glNormalPointer(element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					glEnableClientState(GL_NORMAL_ARRAY);
					break;
				case COLOR:
					GL11.glColorPointer(element.getElementCount(), element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					glEnableClientState(GL_COLOR_ARRAY);
					break;
				case UV:
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + element.getIndex());
					GL11.glTexCoordPointer(element.getElementCount(), element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					glEnableClientState(GL_TEXTURE_COORD_ARRAY);
					break;
				case PADDING:
					break;
				case GENERIC:
					GL20.glEnableVertexAttribArray(element.getIndex());
					GL20.glVertexAttribPointer(element.getIndex(), element.getElementCount(), element.getType().getGlConstant(), false, format.getNextOffset(), format.getOffset(i));
					break;
				default:
					break;
			}
		}
	}

	public final void draw(){
		renderPre();
//		GL11.glDrawArrays(drawMode, 0, vertexCount);
		vbo.drawArrays(drawMode);
		renderPost();
	}

	protected void renderPost(){
		vbo.unbindBuffer();
		for (VertexFormatElement vertexformatelement : format.getElements())
        {
            VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
            int i = vertexformatelement.getIndex();

            switch (vertexformatelement$enumusage)
            {
                case POSITION:
                    glDisableClientState(GL_VERTEX_ARRAY);
                    break;
                case NORMAL:
                    glDisableClientState(GL_NORMAL_ARRAY);
                    break;
                case COLOR:
                    glDisableClientState(GL_COLOR_ARRAY);
                    // is this really needed?
                    GlStateManager.resetColor();
                    break;
                case UV:
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + vertexformatelement.getIndex());
                    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                    break;
                case PADDING:
                    break;
                case GENERIC:
                    glDisableVertexAttribArray(vertexformatelement.getIndex());
                default:
                    FMLLog.severe("Unimplemented vanilla attribute upload: %s", vertexformatelement$enumusage.getDisplayName());
            }
        }
//		vbo.unbind();
	}

	@Override
	public void cleanUp(){
//		vbo.cleanUp();
		vbo.deleteGlBuffers();
	}

}
