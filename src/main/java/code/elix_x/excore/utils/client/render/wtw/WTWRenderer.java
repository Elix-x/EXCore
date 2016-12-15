package code.elix_x.excore.utils.client.render.wtw;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;

public class WTWRenderer {
	
	public static void render(Tessellator borders, Tessellator world){
		render(() -> borders.draw(), () -> world.draw());
	}

	public static void render(Runnable borders, Runnable world){
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
