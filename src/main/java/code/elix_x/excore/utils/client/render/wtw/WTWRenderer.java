package code.elix_x.excore.utils.client.render.wtw;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class WTWRenderer {

	private static Queue<Pair<Runnable, Runnable>> renderingQueue = new LinkedList<Pair<Runnable, Runnable>>();

	static {
		MinecraftForge.EVENT_BUS.register(WTWRenderer.class);
	}

	@SubscribeEvent(priority= EventPriority.LOWEST)
	public static void renderLast(RenderWorldLastEvent event){
		while(!renderingQueue.isEmpty()){
			renderNow(renderingQueue.peek().getLeft(), renderingQueue.poll().getRight());
		}
	}

	public static void render(Runnable borders, Runnable world){
		renderingQueue.add(new ImmutablePair<Runnable, Runnable>(borders, world));
	}

	public static void renderNow(Runnable borders, Runnable world){
		assert Minecraft.getMinecraft().getFramebuffer().isStencilEnabled() : "WTW Renderer can't work without stencils. Please enable them";

		GlStateManager.pushMatrix();
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GlStateManager.colorMask(false, false, false, false);
		GlStateManager.depthMask(false);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 255);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		GL11.glStencilMask(255);
		GlStateManager.clear(GL11.GL_STENCIL_BUFFER_BIT);
		borders.run();
		GlStateManager.depthMask(true);
		GlStateManager.colorMask(true, true, true, true);
		GL11.glStencilMask(0);

		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 255);
		world.run();
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GlStateManager.popMatrix();
	}

}
