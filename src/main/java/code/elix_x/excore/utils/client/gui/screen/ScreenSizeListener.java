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
package code.elix_x.excore.utils.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenSizeListener {

	public int screenWidth;
	public int screenHeight;

	public int prevScreenWidth;
	public int prevScreenHeight;

	public ScreenSizeListener(){
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		screenWidth = prevScreenWidth = res.getScaledWidth();
		screenHeight = prevScreenHeight = res.getScaledHeight();
	}

	public boolean update(){
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int neww = res.getScaledWidth();
		int newh = res.getScaledHeight();
		if(neww != screenWidth || newh != screenHeight){
			prevScreenWidth = screenWidth;
			prevScreenHeight = screenHeight;
			screenWidth = neww;
			screenHeight = newh;
			return true;
		} else{
			return false;
		}
	}

}
