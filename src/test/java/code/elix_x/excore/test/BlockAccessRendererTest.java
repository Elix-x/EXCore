package code.elix_x.excore.test;

import code.elix_x.excore.utils.client.render.world.BlockAccessRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = BlockAccessRendererTest.MODID)
public class BlockAccessRendererTest {

	public static final String MODID = "bartest";

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

			public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){

			}

		}.setRegistryName(MODID, "testblock"));
		GameRegistry.register(new ItemBlock(block).setRegistryName(MODID, "testblock"));
		GameRegistry.registerTileEntity(TestTileEntity.class, new ResourceLocation(MODID, "testblock").toString());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent event){

	}

	@SideOnly(Side.CLIENT)
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ClientRegistry.bindTileEntitySpecialRenderer(TestTileEntity.class, new TestTileEntityRenderer());
	}

	@SubscribeEvent
	public void blockChange(BlockEvent.NeighborNotifyEvent event){
		BlockPos pos = event.getPos();
		for(int x = pos.getX() + 4; x <= pos.getX() + 6; x++){
			for(int y = pos.getY() - 1; y <= pos.getY() + 1; y++){
				for(int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++){
					if(event.getWorld().getTileEntity(new BlockPos(x, y, z)) instanceof TestTileEntity){
						event.getWorld().getTileEntity(new BlockPos(x, y, z)).markDirty();
//						System.out.println("Change at " + pos + " found listener at " + new BlockPos(x, y, z));
					}
				}
			}
		}
	}

	public static class TestTileEntity extends TileEntity {

		@SideOnly(Side.CLIENT)
		BlockAccessRenderer bar;

		@Override
		public void markDirty(){
			super.markDirty();
//			System.out.println("Dirty on " + world.isRemote);
			if(world.isRemote) bar.markDirty();
		}

	}

	@SideOnly(Side.CLIENT)
	public static class TestTileEntityRenderer extends TileEntitySpecialRenderer<TestTileEntity> {

		@Override
		public void renderTileEntityAt(TestTileEntity te, double x, double y, double z, float partialTicks, int destroyStage){
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			if(te.bar == null){
				te.bar = new BlockAccessRenderer(te.getWorld(), new AxisAlignedBB(te.getPos().add(-6, -1, -1), te.getPos().add(-4, 1, 1)), new Vec3d(0, 0, 0));
			}
			te.bar.render();
			te.bar.markDirty();
			GlStateManager.popMatrix();
		}

	}

}
