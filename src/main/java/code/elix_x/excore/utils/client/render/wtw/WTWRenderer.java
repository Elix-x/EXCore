/*******************************************************************************
 * Copyright 2016 Elix_x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package code.elix_x.excore.utils.client.render.wtw;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * <h1>World Through World Renderer</h1><br>
 * Allows rendering of another world through the current world using stencils.<br>
 * It only has the basic functionality. What is outer world, what is inner and how they are rendered is up to users of this class.<br><br>
 * <b>I am irresponsible for any conflicts caused by incorrect usage of this class!</b>
 * @author Elix_x
 *
 */
public class WTWRenderer {

	private static Queue<Pair<Runnable, Runnable>> renderingQueue = new LinkedList<Pair<Runnable, Runnable>>();

	static{
		MinecraftForge.EVENT_BUS.register(WTWRenderer.class);
	}

	/**
	 * <b>INTERNAL - DO NOT USE!</b>
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void renderLast(RenderWorldLastEvent event){
		while(!renderingQueue.isEmpty()){
			renderNow(renderingQueue.peek().getLeft(), renderingQueue.poll().getRight());
		}
	}

	/**
	 * Renders the world through another world, or more specifically, enqueues it for rendering. Everything will be rendered in last possible moment to avoid conflicts.<br>
	 * How everything is drawn is completely up to users of this class.
	 * 
	 * @param tear
	 *            Draw a tear in the world through which the other world can be seen. Only position vertex parameters are used, others are ignored (depth and color buffers stay unmodified).
	 * @param world
	 *            Draw the actual world on the other side of the tear. You can draw everything, only parts visible through the tear will stay.
	 */
	public static void render(Runnable tear, Runnable world){
		renderingQueue.add(new ImmutablePair<Runnable, Runnable>(tear, world));
	}

	/**
	 * Renders the world through another world <b>right now</b>. <b>I am irresponsible for all conflicts caused by usage in wrong time.</b>
	 * 
	 * @param tear
	 *            Draw a tear in the world through which the other world can be seen. Only position vertex parameters are used, others are ignored (depth and color buffers stay unmodified).
	 * @param world
	 *            Draw the actual world on the other side of the tear. You can draw everything, only parts visible through the tear will stay.
	 * @throws Exceptions
	 *             if stencils aren't enabled.
	 */
	public static void renderNow(Runnable tear, Runnable world){
		assert Minecraft.getMinecraft().getFramebuffer().isStencilEnabled() : "WTW Renderer can't work without stencils. Please enable them";

		GlStateManager.pushMatrix();
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GlStateManager.colorMask(false, false, false, false);
		GlStateManager.depthMask(false);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 255);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		GL11.glStencilMask(255);
		GlStateManager.clear(GL11.GL_STENCIL_BUFFER_BIT);
		tear.run();
		GlStateManager.depthMask(true);
		GlStateManager.colorMask(true, true, true, true);
		GL11.glStencilMask(0);

		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 255);
		world.run();
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GlStateManager.popMatrix();
	}

}
