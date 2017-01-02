package code.elix_x.excore.utils.client.render.vbo;

import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import code.elix_x.excomms.reflection.ReflectionHelper.AClass;
import code.elix_x.excomms.reflection.ReflectionHelper.AField;
import code.elix_x.excore.utils.client.render.IVertexBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.fml.common.FMLLog;

public class VertexBufferSingleVBO implements IVertexBuffer {

	protected final VertexFormat format;
	protected final int drawMode;
	protected final int vertexCount;
	private final VBO vbo;

	private VertexBufferSingleVBO(VertexFormat format, int drawMode, int vertexCount, VBO vbo){
		this.format = format;
		this.drawMode = drawMode;
		this.vertexCount = vertexCount;
		this.vbo = vbo;
	}

	public VertexBufferSingleVBO(net.minecraft.client.renderer.VertexBuffer vertexBuffer){
		this(vertexBuffer.getVertexFormat(), vertexBuffer.getDrawMode(), vertexBuffer.getVertexCount(), new VBO());
		ByteBuffer buffer = vertexBuffer.getByteBuffer();
		vbo.bind();
		vbo.data(buffer, GL15.GL_STATIC_DRAW);
		vbo.unbind();
	}

	private static final AClass<net.minecraft.client.renderer.vertex.VertexBuffer> vertexBufferClass = new AClass<>(net.minecraft.client.renderer.vertex.VertexBuffer.class);
	private static final AField<net.minecraft.client.renderer.vertex.VertexBuffer, Integer> glBufferId = vertexBufferClass.<Integer> getDeclaredField("glBufferId", "field_177365_a").setAccessible(true);
	private static final AField<net.minecraft.client.renderer.vertex.VertexBuffer, VertexFormat> vertexFormat = vertexBufferClass.<VertexFormat> getDeclaredField("vertexFormat", "field_177363_b").setAccessible(true);
	private static final AField<net.minecraft.client.renderer.vertex.VertexBuffer, Integer> count = vertexBufferClass.<Integer> getDeclaredField("count", "field_177363_b").setAccessible(true);

	public VertexBufferSingleVBO(net.minecraft.client.renderer.vertex.VertexBuffer vertexBuffer, int drawMode){
		this(vertexFormat.get(vertexBuffer), drawMode, count.get(vertexBuffer), new VBO(glBufferId.get(vertexBuffer)));
	}
	
	private boolean modifyClientStates = true;
	
	public VertexBufferSingleVBO setModifyClientStates(boolean modifyClientStates){
		this.modifyClientStates = modifyClientStates;
		return this;
	}

	protected void renderPre(){
		vbo.bind();
		for(int i = 0; i < format.getElementCount(); i++){
			VertexFormatElement element = format.getElement(i);
			switch(element.getUsage()){
				case POSITION:
					GL11.glVertexPointer(element.getElementCount(), element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					if(modifyClientStates) glEnableClientState(GL_VERTEX_ARRAY);
					break;
				case NORMAL:
					GL11.glNormalPointer(element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					if(modifyClientStates) glEnableClientState(GL_NORMAL_ARRAY);
					break;
				case COLOR:
					GL11.glColorPointer(element.getElementCount(), element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					if(modifyClientStates) glEnableClientState(GL_COLOR_ARRAY);
					break;
				case UV:
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + element.getIndex());
					GL11.glTexCoordPointer(element.getElementCount(), element.getType().getGlConstant(), format.getNextOffset(), format.getOffset(i));
					if(modifyClientStates) glEnableClientState(GL_TEXTURE_COORD_ARRAY);
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
		GlStateManager.glDrawArrays(drawMode, 0, vertexCount);
		renderPost();
	}

	protected void renderPost(){
		for(VertexFormatElement vertexformatelement : format.getElements()){
			VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
			int i = vertexformatelement.getIndex();

			switch(vertexformatelement$enumusage){
				case POSITION:
					if(modifyClientStates) glDisableClientState(GL_VERTEX_ARRAY);
					break;
				case NORMAL:
					if(modifyClientStates) glDisableClientState(GL_NORMAL_ARRAY);
					break;
				case COLOR:
					if(modifyClientStates) glDisableClientState(GL_COLOR_ARRAY);
					// is this really needed?
					GlStateManager.resetColor();
					break;
				case UV:
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + vertexformatelement.getIndex());
					if(modifyClientStates) glDisableClientState(GL_TEXTURE_COORD_ARRAY);
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
		vbo.unbind();
	}

	@Override
	public void cleanUp(){
		vbo.cleanUp();
	}

}
