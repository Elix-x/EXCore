package code.elix_x.excore.utils.client.render.world;

import java.nio.FloatBuffer;

import code.elix_x.excore.utils.client.render.IVertexBuffer;
import code.elix_x.excore.utils.client.render.OpenGLHelper;
import code.elix_x.excore.utils.client.render.vbo.VertexBufferSingleVBO;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockAccessRenderer {

	private final IBlockAccess world;
	// Shapes API has to be rewritten to support IBlockAccesses
	// private final Shape3D shape;
	private final AxisAlignedBB shape;

	private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
	private final IVertexBuffer[] vertexBuffers = new IVertexBuffer[BlockRenderLayer.values().length];
	private boolean needsUpdate = true;

	public BlockAccessRenderer(IBlockAccess world, AxisAlignedBB shape){
		this.world = world;
		this.shape = shape;
	}

	public void markDirty(){
		needsUpdate = true;
	}

	public void render(){
		if(needsUpdate){
			rebuildBuffers();
			needsUpdate = false;
		}
	}

	protected void rebuildBuffers(){
		cleanUp();
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			net.minecraft.client.renderer.VertexBuffer buffer = new net.minecraft.client.renderer.VertexBuffer(16);
			BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
			for(int x = (int) shape.minX; x < shape.maxX; x++){
				for(int y = (int) shape.minY; y < shape.maxY; y++){
					for(int z = (int) shape.minZ; z < shape.maxZ; z++){
						BlockPos pos = new BlockPos(x, y, z);
						IBlockState state = world.getBlockState(pos);
						if(state.getBlock().canRenderInLayer(state, layer))
							blockRenderer.renderBlock(state, pos, world, buffer);
					}
				}
			}
			vertexBuffers[layer.ordinal()] = new VertexBufferSingleVBO(buffer).setModifyClientStates(false);
		}
		OpenGLHelper.enableClientState(DefaultVertexFormats.BLOCK);
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			vertexBuffers[layer.ordinal()].draw();
		}
		OpenGLHelper.disableClientState(DefaultVertexFormats.BLOCK);
	}

	protected void cleanUp(){
		for(IVertexBuffer buffer : vertexBuffers){
			if(buffer != null) buffer.cleanUp();
		}
	}

}
