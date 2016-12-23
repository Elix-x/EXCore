package code.elix_x.excore.test;

import org.lwjgl.opengl.GL11;

import code.elix_x.excore.utils.client.render.item.ItemStackRenderer;
import code.elix_x.excore.utils.client.render.wtw.WTWRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = WTWRendererTest.MODID)
public class WTWRendererTest {

	public static final String MODID = "wtwrenderertest";

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

		@Override
		public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage){
			WTWRenderer.render(() -> {
				
				renderStencil(x, y, z);
				
			}, () -> {
				
				render(x, y, z);
				
			});
		}
		
		void renderStencil(double x, double y, double z){
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			Tessellator tess = Tessellator.getInstance();
			VertexBuffer buff = tess.getBuffer();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			buff.pos(0, 0, 0).endVertex();
			buff.pos(0, 1, 0).endVertex();
			buff.pos(1, 1, 0).endVertex();
			buff.pos(1, 0, 0).endVertex();
			tess.draw();
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
		}
		
		void render(double x, double y, double z){
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.pushMatrix();
			GlStateManager.scale(10, 10, 1);
			GlStateManager.translate(-0.5, -0.5, 10);
			Tessellator tess = Tessellator.getInstance();
			VertexBuffer buff = tess.getBuffer();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			buff.pos(0, 0, 0).tex(0, 1).endVertex();
			buff.pos(0, 1, 0).tex(0, 0).endVertex();
			buff.pos(1, 1, 0).tex(1, 0).endVertex();
			buff.pos(1, 0, 0).tex(1, 1).endVertex();
			tess.draw();
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

	}

}