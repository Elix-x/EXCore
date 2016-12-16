package code.elix_x.excore.utils.client.render.wtw;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import code.elix_x.excore.EXCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class WTWRenderer {

	private static Queue<Pair<Runnable, Runnable>> renderingQueue = new LinkedList<Pair<Runnable, Runnable>>();

	@SubscribeEvent(priority= EventPriority.LOWEST)
	static void renderLast(RenderWorldLastEvent event){
		while(!renderingQueue.isEmpty()){
			renderNow(renderingQueue.peek().getLeft(), renderingQueue.poll().getRight());
		}
	}

	public static void render(Runnable borders, Runnable world){
		renderingQueue.add(new ImmutablePair<Runnable, Runnable>(borders, world));
	}

	public static void renderNow(Runnable borders, Runnable world){
		assert Minecraft.getMinecraft().getFramebuffer().isStencilEnabled() : "WTW Renderer can't work without stencils. Please enable them";

		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GlStateManager.colorMask(false, false, false, false);
		GL11.glDepthMask(false);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 255);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		GL11.glStencilMask(255);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		borders.run();
		GL11.glDepthMask(true);
		GL11.glColorMask(true, true, true, true);
		GL11.glStencilMask(0);

		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 255);
		world.run();
		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}

}
