package code.elix_x.excore.test;

import org.lwjgl.opengl.GL11;

import code.elix_x.excore.utils.client.render.VertexBufferSeparate;
import code.elix_x.excore.utils.client.render.VertexBufferSingle;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

		private code.elix_x.excore.utils.client.render.VertexBuffer buffer;
		
		@Override
		public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage){
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.disableTexture2D();
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GlStateManager.enableBlend();
			/*Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vb = tessellator.getBuffer();
			vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			vb.pos(0, 0, 0).color(1f, 1f, 1f, 0.5f).endVertex();
			vb.pos(1, 0, 0).color(1f, 1f, 1f, 0.5f).endVertex();
			vb.pos(1, 1, 0).color(1f, 1f, 1f, 0.5f).endVertex();
			vb.pos(0, 1, 0).color(1f, 1f, 1f, 0.5f).endVertex();
			tessellator.draw();*/
			/*vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			vb.pos(0, 0, 0).tex(1, 1).endVertex();
			vb.pos(0, 1, 0).tex(1, 0).endVertex();
			vb.pos(1, 1, 0).tex(0, 0).endVertex();
			vb.pos(1, 0, 0).tex(0, 1).endVertex();*/
			if(buffer == null){
				VertexBuffer buffer = new VertexBuffer(16);
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
				buffer.pos(0, 0, 0).color(1f, 1f, 1f, 0.5f).endVertex();
				buffer.pos(1, 0, 0).color(1f, 1f, 1f, 0.5f).endVertex();
				buffer.pos(1, 1, 0).color(1f, 1f, 1f, 0.5f).endVertex();
				buffer.pos(0, 1, 0).color(1f, 1f, 1f, 0.5f).endVertex();
				/*buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				buffer.pos(0, 0, 0).tex(1, 1).endVertex();
				buffer.pos(0, 1, 0).tex(1, 0).endVertex();
				buffer.pos(1, 1, 0).tex(0, 0).endVertex();
				buffer.pos(1, 0, 0).tex(0, 1).endVertex();*/
				buffer.finishDrawing();
				this.buffer = new VertexBufferSingle(buffer);
				System.out.println("Uploaded");
			}
			buffer.draw();
//			buffer.cleanUp();
//			buffer = null;
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
		}

	}

}
