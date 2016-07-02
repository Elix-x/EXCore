package code.elix_x.excore.utils.client.render;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemStackRenderer {

	public static void renderItemStack(Minecraft minecraft, int xPosition, int yPosition, ItemStack itemstack){
		minecraft.getRenderItem().renderItemAndEffectIntoGUI(null, itemstack, xPosition, yPosition);
		minecraft.getRenderItem().renderItemOverlayIntoGUI(getFontRenderer(minecraft, itemstack), itemstack, xPosition, yPosition, null);
	}

	public static List<String> getTooltip(Minecraft minecraft, ItemStack itemstack){
		List<String> list = itemstack.getTooltip(minecraft.thePlayer, minecraft.gameSettings.advancedItemTooltips);
		for(int k = 0; k < list.size(); ++k){
			if(k == 0){
				list.set(k, itemstack.getRarity().rarityColor + list.get(k));
			} else{
				list.set(k, TextFormatting.GRAY + list.get(k));
			}
		}
		return list;
	}

	public static FontRenderer getFontRenderer(Minecraft minecraft, ItemStack itemstack){
		FontRenderer fontRenderer = itemstack.getItem().getFontRenderer(itemstack);
		if(fontRenderer == null){
			fontRenderer = minecraft.fontRendererObj;
		}
		return fontRenderer;
	}

}
