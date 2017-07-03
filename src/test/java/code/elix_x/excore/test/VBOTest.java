package code.elix_x.excore.test;

import code.elix_x.excore.utils.client.render.IVertexBuffer;
import code.elix_x.excore.utils.client.render.vbo.VertexBufferSingleVBO;
import code.elix_x.excore.utils.registry.RegistrationQueue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
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

@Mod(modid = VBOTest.MODID)
public class VBOTest {

	public static final String MODID = "vbotest";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
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
		GameRegistry.registerTileEntity(TestTileEntity.class, new ResourceLocation(MODID, "testblock").toString());
	}

	@SideOnly(Side.CLIENT)
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ClientRegistry.bindTileEntitySpecialRenderer(TestTileEntity.class, new TestTileEntityRenderer());
	}

	public static class TestTileEntity extends TileEntity {

	}

	@SideOnly(Side.CLIENT)
	public static class TestTileEntityRenderer extends TileEntitySpecialRenderer {

		private IVertexBuffer buffer;

		@Override
		public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0, 1, 0);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			BufferBuilder vbuffer = new BufferBuilder(16);
			vbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			vbuffer.pos(0, 0, 0).tex(0, 1).endVertex();
			vbuffer.pos(1, 0, 0).tex(1, 1).endVertex();
			vbuffer.pos(1, 1, 0).tex(1, 0).endVertex();
			vbuffer.pos(0, 1, 0).tex(0, 0).endVertex();
			vbuffer.finishDrawing();
			if(buffer == null){
				buffer = new VertexBufferSingleVBO(vbuffer);
			}
			buffer.draw();
			GlStateManager.popMatrix();
		}

	}

}
