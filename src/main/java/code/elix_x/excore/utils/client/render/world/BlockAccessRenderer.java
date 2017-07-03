package code.elix_x.excore.utils.client.render.world;

import java.nio.FloatBuffer;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.GL11;

import code.elix_x.excore.utils.client.render.IVertexBuffer;
import code.elix_x.excore.utils.client.render.OpenGLHelper;
import code.elix_x.excore.utils.client.render.vbo.VertexBufferSingleVBO;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

public class BlockAccessRenderer {

	private final IBlockAccess world;
	// Shapes API has to be rewritten to support IBlockAccesses
	// private final Shape3D shape;
	private final AxisAlignedBB shape;
	private final AxisAlignedBB shapeResult;

	private final IVertexBuffer[] vertexBuffers = new IVertexBuffer[BlockRenderLayer.values().length];
	private boolean needsUpdate = true;

	public BlockAccessRenderer(IBlockAccess world, AxisAlignedBB shape, AxisAlignedBB shapeResult){
		this.world = world;
		this.shape = shape.expand(0.5, 0.5, 0.5).offset(0.5, 0.5, 0.5);
		this.shapeResult = shapeResult;
	}

	public BlockAccessRenderer(IBlockAccess world, AxisAlignedBB shape, Vec3d posResult){
		this(world, shape, shape.offset(posResult.subtract(shape.getCenter())));
	}

	public BlockAccessRenderer(IBlockAccess world, AxisAlignedBB shape){
		this(world, shape, shape);
	}

	public void markDirty(){
		needsUpdate = true;
	}

	public void render(){
		if(needsUpdate){
			rebuildBuffers();
			needsUpdate = false;
		}
		OpenGLHelper.enableClientState(DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		Minecraft.getMinecraft().entityRenderer.enableLightmap();
		GlStateManager.pushMatrix();
		double scaleX = (shapeResult.maxX - shapeResult.minX) / (shape.maxX - shape.minX);
		double scaleY = (shapeResult.maxY - shapeResult.minY) / (shape.maxY - shape.minY);
		double scaleZ = (shapeResult.maxZ - shapeResult.minZ) / (shape.maxZ - shape.minZ);
		GlStateManager.scale(scaleX, scaleY, scaleZ);
		GlStateManager.translate(shapeResult.getCenter().x / scaleX, shapeResult.getCenter().y / scaleY, shapeResult.getCenter().z / scaleZ);
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			// TODO layer specific GL setups
			vertexBuffers[layer.ordinal()].draw();
		}
		GlStateManager.popMatrix();
		Minecraft.getMinecraft().entityRenderer.disableLightmap();
		RenderHelper.enableStandardItemLighting();
		OpenGLHelper.disableClientState(DefaultVertexFormats.BLOCK);
	}

	protected void rebuildBuffers(){
		cleanUp();
		BlockRenderLayer prev = MinecraftForgeClient.getRenderLayer();
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			ForgeHooksClient.setRenderLayer(layer);
			BufferBuilder buffer = Tessellator.getInstance().getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			buffer.setTranslation(-shape.getCenter().x, -shape.getCenter().y, -shape.getCenter().z);
			BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
			for(int x = (int) Math.floor(shape.minX); x < shape.maxX; x++){
				for(int y = (int) Math.floor(shape.minY); y < shape.maxY; y++){
 					for(int z = (int) Math.floor(shape.minZ); z < shape.maxZ; z++){
						BlockPos pos = new BlockPos(x, y, z);
						IBlockState state = world.getBlockState(pos);
						if(state.getBlock().canRenderInLayer(state, layer)){
							blockRenderer.renderBlock(state, pos, world, buffer);
						}
					}
				}
			}
			buffer.finishDrawing();
			buffer.setTranslation(0, 0, 0);
			vertexBuffers[layer.ordinal()] = new VertexBufferSingleVBO(buffer).setModifyClientStates(false);
		}
		ForgeHooksClient.setRenderLayer(prev);
	}

	protected void cleanUp(){
		for(IVertexBuffer buffer : vertexBuffers){
			if(buffer != null) buffer.cleanUp();
		}
	}

}
