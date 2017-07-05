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

import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

import code.elix_x.excore.utils.client.render.FrameBufferSnapshot;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.IBlockAccess;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class WTWRenderer implements Runnable {

	private static final WTWRenderer INSTANCE = new WTWRenderer();
	private static Stack<WTWRenderer> current = new Stack<>();
	private static int depth = 0;

	static {
		MinecraftForge.EVENT_BUS.register(WTWRenderer.class);
		current.push(INSTANCE);
	}

	@SubscribeEvent
	public static void renderWorldLast(RenderWorldLastEvent event){
		INSTANCE.run();
	}

	public static WTWRenderer instance(){
		return current.peek();
	}

	public static void pushInstance(){
		current.push(new WTWRenderer());
	}

	public static WTWRenderer popInstance(){
		return current.pop();
	}

	private FrameBufferSnapshot SNAPSHOT;
	private boolean depthReadFlag;
	private boolean stencilReadFlag;
	private boolean depthStencilReadFlag;

	private Multimap<Phase, Consumer<WTWRenderer>> renderers = ArrayListMultimap.create();

	private void render(@Nonnull Phase phase, Consumer<WTWRenderer> renderer){
		renderers.put(phase, renderer);
	}

	private void render(@Nonnull Phase phase, Runnable renderer){
		render(phase, wtw -> renderer.run());
	}

	public void run(){
		//TODO user config
		if(depth > 16) return;
		depth++;
		depthReadFlag = false;
		stencilReadFlag = false;
		depthStencilReadFlag = false;
		for(Phase phase : Phase.values()){
			if(renderers.containsKey(phase)){
				phase.phasePre(this);
				renderers.get(phase).forEach(renderer -> renderer.accept(this));
				phase.phasePost(this);
			}
		}
		if(SNAPSHOT != null) SNAPSHOT.delete();
		renderers.clear();
		depth--;
	}

	public enum Phase {

		NORMAL{

			public void render(Runnable... phaseSpecifics){
				instance().render(this, phaseSpecifics[0]);
			}

		},
		DEPTHREADONLY{
			@Override
			void phasePre(WTWRenderer wtw){
				GlStateManager.depthMask(false);
			}

			public void render(Runnable... phaseSpecifics){
				instance().render(this, phaseSpecifics[0]);
			}

			@Override
			void phasePost(WTWRenderer wtw){
				GlStateManager.depthMask(true);
			}
		}, STENCIL{
			@Override
			void phasePre(WTWRenderer wtw){
				assert Minecraft.getMinecraft().getFramebuffer().isStencilEnabled() : "WTW Renderer can't work without stencils. Please enable them";

				GL11.glEnable(GL11.GL_STENCIL_TEST);
				if(wtw.SNAPSHOT == null) wtw.SNAPSHOT = new FrameBufferSnapshot();
				if(!wtw.stencilReadFlag){
					wtw.SNAPSHOT.readFrom(GL11.GL_STENCIL_BUFFER_BIT);
					wtw.stencilReadFlag = true;
				}
			}

			public void render(Runnable... phaseSpecifics){
				instance().render(this, wtw -> {
					wtw.SNAPSHOT.writeTo(GL11.GL_STENCIL_BUFFER_BIT);

					GlStateManager.colorMask(false, false, false, false);
					GlStateManager.depthMask(false);
					GL11.glStencilFunc(GL11.GL_GEQUAL, depth - 1, 255);
					GL11.glStencilMask(255);
					GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_INCR);
					phaseSpecifics[0].run();
					GlStateManager.depthMask(true);
					GlStateManager.colorMask(true, true, true, true);
					GL11.glStencilMask(0);

					GL11.glStencilFunc(GL11.GL_GEQUAL, depth, 255);
					phaseSpecifics[1].run();
				});
			}

			@Override
			void phasePost(WTWRenderer wtw){
				GL11.glDisable(GL11.GL_STENCIL_TEST);
			}
		}, DEPTHREADWRITE{

			@Override
			void phasePre(WTWRenderer wtw){
				if(wtw.SNAPSHOT == null) wtw.SNAPSHOT = new FrameBufferSnapshot();
				if(!wtw.depthReadFlag){
					wtw.SNAPSHOT.readFrom(GL11.GL_DEPTH_BUFFER_BIT);
					wtw.depthReadFlag = true;
				}
			}

			public void render(Runnable... phaseSpecifics){
				instance().render(this, wtw -> {
					wtw.SNAPSHOT.writeTo(GL11.GL_DEPTH_BUFFER_BIT);
					phaseSpecifics[0].run();
				});
			}

			@Override
			void phasePost(WTWRenderer wtw){
				wtw.SNAPSHOT.writeTo(GL11.GL_DEPTH_BUFFER_BIT);
			}

		}, STENCILDEPTHREADWRITE{

			@Override
			void phasePre(WTWRenderer wtw){
				assert Minecraft.getMinecraft().getFramebuffer().isStencilEnabled() : "WTW Renderer can't work without stencils. Please enable them";

				GL11.glEnable(GL11.GL_STENCIL_TEST);
				if(wtw.SNAPSHOT == null) wtw.SNAPSHOT = new FrameBufferSnapshot();
				if(!wtw.depthStencilReadFlag){
					wtw.SNAPSHOT.readFrom(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
					wtw.depthStencilReadFlag = true;
				}
			}

			public void render(Runnable... phaseSpecifics){
				instance().render(this, wtw -> {
					wtw.SNAPSHOT.writeTo(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

					GlStateManager.colorMask(false, false, false, false);
					GlStateManager.depthMask(false);
					GL11.glStencilFunc(GL11.GL_GEQUAL, depth - 1, 255);
					GL11.glStencilMask(255);
					GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_INCR);
					phaseSpecifics[0].run();
					GlStateManager.depthMask(true);
					GlStateManager.colorMask(true, true, true, true);
					GL11.glStencilMask(0);

					GL11.glStencilFunc(GL11.GL_GEQUAL, depth, 255);
					phaseSpecifics[1].run();
				});
			}

			@Override
			void phasePost(WTWRenderer wtw){
				GL11.glDisable(GL11.GL_STENCIL_TEST);
				wtw.SNAPSHOT.writeTo(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			}

		};

		public abstract void render(Runnable... phaseSpecifics);

		void phasePre(WTWRenderer wtw){

		}

		void phasePost(WTWRenderer wtw){

		}

	}


}
