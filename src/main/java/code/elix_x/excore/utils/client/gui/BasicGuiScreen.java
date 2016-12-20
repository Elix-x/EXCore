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
package code.elix_x.excore.utils.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;

public class BasicGuiScreen extends GuiScreen {

	public final GuiScreen parent;

	protected int guiWidth;
	protected int guiHeight;

	protected int xPos;
	protected int yPos;

	public BasicGuiScreen(GuiScreen parent, int guiWidth, int guiHeight){
		this.parent = parent;
		this.guiWidth = guiWidth;
		this.guiHeight = guiHeight;
	}

	@Override
	public void initGui(){
		xPos = (width - guiWidth) / 2;
		yPos = (height - guiHeight) / 2;
	}

	@Override
	protected void keyTyped(char c, int key) throws IOException{
		if(key == 1){
			onClose();
			this.mc.displayGuiScreen(parent);
			return;
		}
	}

	protected void onClose(){

	}

}
