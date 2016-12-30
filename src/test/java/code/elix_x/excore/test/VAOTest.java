package code.elix_x.excore.test;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import code.elix_x.excore.utils.client.render.IVertexBuffer;
import code.elix_x.excore.utils.client.render.vbo.VertexBufferSingleVBO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = VAOTest.MODID)
public class VAOTest {

	public static final String MODID = "vaotest";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Block block;
		GameRegistry.register(block = new Block(Material.ANVIL){

			public boolean hasTileEntity(IBlockState state){
				return true;
			}

			@Override
			public TileEntity createTileEntity(World world, IBlockState state){
				return new TestTileEntity();
			}

			public boolean isOpaqueCube(IBlockState state){
				return false;
			}

			public EnumBlockRenderType getRenderType(IBlockState state){
				return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
			}

		}.setRegistryName(MODID, "testblock"));
		GameRegistry.register(new ItemBlock(block).setRegistryName(MODID, "testblock"));
		GameRegistry.registerTileEntity(TestTileEntity.class, new ResourceLocation(MODID, "testblock").toString());
	}

	@SideOnly(Side.CLIENT)
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		Minecraft.getMinecraft().getFramebuffer().enableStencil();
		ClientRegistry.bindTileEntitySpecialRenderer(TestTileEntity.class, new TestTileEntityRenderer());
	}

	public static class TestTileEntity extends TileEntity {

	}

	@SideOnly(Side.CLIENT)
	public static class TestTileEntityRenderer extends TileEntitySpecialRenderer {

		private IVertexBuffer buffer;
		
		@Override
		public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage){
			/*FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
			GlStateManager.pushMatrix();
	        GlStateManager.loadIdentity();
	        float f = 1.000001F;
	        GlStateManager.translate(-8.0F, -8.0F, -8.0F);
	        GlStateManager.scale(1.000001F, 1.000001F, 1.000001F);
	        GlStateManager.translate(8.0F, 8.0F, 8.0F);
	        GlStateManager.getFloat(2982, modelviewMatrix);
	        GlStateManager.popMatrix();*/
	        
	    	GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0, 1, 0);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			VertexBuffer vbuffer = new VertexBuffer(16);
			vbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			vbuffer.pos(0, 0, 0).tex(0, 1).endVertex();
			vbuffer.pos(1, 0, 0).tex(1, 1).endVertex();
			vbuffer.pos(1, 1, 0).tex(1, 0).endVertex();
			vbuffer.pos(0, 1, 0).tex(0, 0).endVertex();
			vbuffer.finishDrawing();
			IVertexBuffer buffer = new VertexBufferSingleVBO(vbuffer);
			buffer.draw();
			buffer.cleanUp();
			buffer = null;
			
			/*GlStateManager.disableAlpha();
	        GlStateManager.multMatrix(modelviewMatrix);
			
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().entityRenderer.enableLightmap();
			GlStateManager.matrixMode(5888);
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vbuffer = tessellator.getBuffer();
			vbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(te.getWorld().getBlockState(te.getPos().down()), te.getPos().down(), te.getWorld(), vbuffer);
//			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(Blocks.DIRT.getDefaultState(), 0.5f);
			tessellator.draw();
			vbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			vbuffer.pos(0, 0, 0).tex(0, 1).endVertex();
			vbuffer.pos(1, 0, 0).tex(1, 1).endVertex();
			vbuffer.pos(1, 1, 0).tex(1, 0).endVertex();
			vbuffer.pos(0, 1, 0).tex(0, 0).endVertex();
			tessellator.draw();
			Minecraft.getMinecraft().entityRenderer.disableLightmap();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableAlpha();*/
			GlStateManager.popMatrix();
		}

	}

}
