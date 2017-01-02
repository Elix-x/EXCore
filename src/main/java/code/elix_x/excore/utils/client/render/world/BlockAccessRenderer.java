package code.elix_x.excore.utils.client.render.world;

import java.nio.FloatBuffer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import scala.actors.threadpool.Arrays;

public class BlockAccessRenderer {

	private final IBlockAccess world;
	// Shapes API has to be rewritten to support IBlockAccesses
	// private final Shape3D shape;
	private final AxisAlignedBB shape;

	private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
	private final VertexBuffer[] vertexBuffers = new VertexBuffer[BlockRenderLayer.values().length];
	private final VertexBufferUploader uploader = new VertexBufferUploader();
	private boolean needsUpdate = true;

	public BlockAccessRenderer(IBlockAccess world, AxisAlignedBB shape){
		this.world = world;
		this.shape = shape;
		Arrays.fill(vertexBuffers, new VertexBuffer(DefaultVertexFormats.BLOCK));
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
			VertexBuffer vbo = new VertexBuffer(buffer.getVertexFormat());
			vbo.bufferData(buffer.getByteBuffer());
			vertexBuffers[layer.ordinal()] = vbo;
		}
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			VertexBuffer buffer = vertexBuffers[layer.ordinal()];
			buffer.bindBuffer();
			//TODO: DRAW
			buffer.unbindBuffer();
		}
	}

	protected void cleanUp(){
		for(VertexBuffer buffer : vertexBuffers){
			buffer.deleteGlBuffers();
		}
	}

}
