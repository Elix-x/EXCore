package code.elix_x.excore.xmas;

import code.elix_x.excore.EXCore;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class XMasSpecial {

	private static final ResourceLocation HAT = new ResourceLocation(EXCore.MODID, "textures/misc/santa_hat.png");
	private static final SoundEvent XMASTIME = new SoundEvent(new ResourceLocation(EXCore.MODID, "misc.xmas_time"));

	public XMasSpecial(){
		GameRegistry.register(XMASTIME, XMASTIME.getSoundName());
	}

	private ISound xmastime;

	@SubscribeEvent
	public void drawGui(DrawScreenEvent event){
		if(event.getGui() instanceof GuiMainMenu){
			GlStateManager.pushMatrix();
			GlStateManager.translate(event.getGui().width / 2 + 113, 13, 0);
			GlStateManager.scale(4, 4, 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(HAT);
			Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 10, 10, 10, 10);
			GlStateManager.popMatrix();
		}
	}

	@SubscribeEvent
	public void playSound(PlayerTickEvent event){
		if(event.phase == Phase.END){
			if(event.player.openContainer instanceof ContainerChest){
				if(xmastime == null){
					ContainerChest conChest = (ContainerChest) event.player.openContainer;
					if(conChest.getLowerChestInventory() instanceof TileEntityChest){
						if(event.player.world.getBlockState(((TileEntityChest) conChest.getLowerChestInventory()).getPos().up()).getBlock() instanceof BlockLeaves){
							Minecraft.getMinecraft().getSoundHandler().playSound(xmastime = new PositionedSoundRecord(XMASTIME, SoundCategory.PLAYERS, 1, 1, ((TileEntityChest) conChest.getLowerChestInventory()).getPos()));
						}
					}
				}
			} else{
				Minecraft.getMinecraft().getSoundHandler().stopSound(xmastime);
				xmastime = null;
			}
		}
	}

}
