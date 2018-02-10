package code.elix_x.excore.test;

import code.elix_x.excore.utils.client.render.wtw.WTWRenderer;
import code.elix_x.excore.utils.registry.RegistrationQueue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
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
import org.lwjgl.opengl.GL11;

@Mod(modid = WTWRendererTest.MODID)
public class WTWRendererTest {

	public static final String MODID = "wtwrenderertest";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		GameRegistry.registerTileEntity(TestTileEntity.class, new ResourceLocation(MODID, "testblock").toString());
		Block block;
		new RegistrationQueue().enqueue(block = new Block(Material.ANVIL){

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

		}.setRegistryName(MODID, "testblock")).enqueue(new ItemBlock(block).setRegistryName(MODID, "testblock"));
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
		public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
			WTWRenderer.pushInstance();
			WTWRenderer.Phase.NORMAL.render(() ->{
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);
				GlStateManager.disableCull();
				Tessellator tess = Tessellator.getInstance();
				BufferBuilder buff = tess.getBuffer();
				buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				bindTexture(TextureMap.LOCATION_MISSING_TEXTURE);
				buff.pos(0, 0, 0).tex(0.25, 0.75).endVertex();
				buff.pos(0, 0, 1).tex(0.25, 0.25).endVertex();
				buff.pos(1, 0, 1).tex(0.75, 0.25).endVertex();
				buff.pos(1, 0, 0).tex(0.75, 0.75).endVertex();
				tess.draw();
				GlStateManager.enableCull();
				GlStateManager.popMatrix();
			});
			WTWRenderer.Phase.STENCIL.render(() -> renderStencil(x + 0.5, y + 0.5, z + 2, 1), () -> render(x, y, z + 1, false));
			WTWRenderer.Phase.STENCIL.render(() -> renderStencil(x + 0.5, y + 1.5, z + 1, 1), () -> render(x, y, z, true));
			WTWRenderer wtw = WTWRenderer.popInstance();
			WTWRenderer.Phase.STENCILDEPTHREADWRITE.render(() -> renderStencil(x + 0.5, y + 0.5, z, 1), wtw);
		}
		
		void renderStencil(double x, double y, double z, int open){
			float opening = open;
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buff = tess.getBuffer();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			buff.pos(-opening/2, -opening/2, 0).color(opening * 0.5f, 0, 1 - opening * 0.5f, 0.25f).endVertex();
			buff.pos(-opening/2, +opening/2, 0).color(opening * 0.5f, 0, 1 - opening * 0.5f, 0.25f).endVertex();
			buff.pos(+opening/2, +opening/2, 0).color(opening * 0.5f, 0, 1 - opening * 0.5f, 0.25f).endVertex();
			buff.pos(+opening/2, -opening/2, 0).color(opening * 0.5f, 0, 1 - opening * 0.5f, 0.25f).endVertex();
			tess.draw();
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
		}

		void render(double x, double y, double z, boolean blocks){
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.pushMatrix();
			GlStateManager.scale(10, 10, 1);
			GlStateManager.translate(-0.5, -0.5, 7.5);
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buff = tess.getBuffer();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			bindTexture(blocks ? TextureMap.LOCATION_BLOCKS_TEXTURE : TextureMap.LOCATION_MISSING_TEXTURE);
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
