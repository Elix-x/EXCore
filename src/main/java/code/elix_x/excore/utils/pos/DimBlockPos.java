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
package code.elix_x.excore.utils.pos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class DimBlockPos extends BlockPos {

	public int dimId;

	private DimBlockPos(){
		super(0, 0, 0);
	}

	public DimBlockPos(int x, int y, int z, int dimId){
		super(x, y, z);
		this.dimId = dimId;
	}

	public DimBlockPos(net.minecraft.util.math.BlockPos pos, int dimId){
		this(pos.getX(), pos.getY(), pos.getZ(), dimId);
	}

	public DimBlockPos(TileEntity te){
		this(te.getPos(), te.getWorld().provider.getDimension());
	}

	public int getDimId(){
		return dimId;
	}

	public void setDimId(int dimId){
		this.dimId = dimId;
	}

	public World getWorld(){
		return DimensionManager.getWorld(dimId);
	}

	public TileEntity getTileEntity(){
		return getTileEntity(getWorld());
	}

	public IBlockState getBlockState(){
		return getBlockState(getWorld());
	}

	public Block getBlock(){
		return getBlock(getWorld());
	}

	public int getMetadata(World world){
		return getMetadata(getWorld());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt = super.writeToNBT(nbt);
		nbt.setInteger("dimId", dimId);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		dimId = nbt.getInteger("dimId");
	}

	public static DimBlockPos createFromNBT(NBTTagCompound nbt){
		return new DimBlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getInteger("dimId"));
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dimId;
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(getClass() != obj.getClass()) return false;
		DimBlockPos other = (DimBlockPos) obj;
		if(dimId != other.dimId) return false;
		return true;
	}

}
