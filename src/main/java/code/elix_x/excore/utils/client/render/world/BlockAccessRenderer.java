package code.elix_x.excore.utils.client.render.world;

import java.nio.FloatBuffer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.VertexBuffer;
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
	private final VertexBuffer.State[] vertexBuffers = new VertexBuffer.State[BlockRenderLayer.values().length];
	private final WorldVertexBufferUploader uploader = new WorldVertexBufferUploader();
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
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			VertexBuffer buffer = new VertexBuffer(0);
			buffer.setVertexState(vertexBuffers[layer.ordinal()]);
			uploader.draw(buffer);
		}
	}

	protected void rebuildBuffers(){
		for(BlockRenderLayer layer : BlockRenderLayer.values()){
			VertexBuffer buffer = new VertexBuffer(16);
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
			vertexBuffers[layer.ordinal()] = buffer.getVertexState();
		}
	}

}
