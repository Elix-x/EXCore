package code.elix_x.excore.utils.pos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockPos {

	public int x;
	public int y;
	public int z;

	private BlockPos(){

	}

	public BlockPos(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPos(net.minecraft.util.math.BlockPos pos){
		this(pos.getX(), pos.getY(), pos.getZ());
	}

	public BlockPos(TileEntity te){
		this(te.getPos());
	}

	public int getX(){
		return x;
	}

	public void setX(int x){
		this.x = x;
	}

	public int getY(){
		return y;
	}

	public void setY(int y){
		this.y = y;
	}

	public int getZ(){
		return z;
	}

	public void setZ(int z){
		this.z = z;
	}

	public BlockPos offset(EnumFacing direction){
		return offsetX(direction.getDirectionVec().getX()).offsetY(direction.getDirectionVec().getY()).offsetZ(direction.getDirectionVec().getZ());
	}

	public BlockPos offsetX(int xx){
		x += xx;
		return this;
	}

	public BlockPos offsetY(int yy){
		y += yy;
		return this;
	}

	public BlockPos offsetZ(int zz){
		z += zz;
		return this;
	}

	public BlockPos offsetNew(EnumFacing direction){
		return offsetXNew(direction.getDirectionVec().getX()).offsetYNew(direction.getDirectionVec().getY()).offsetZNew(direction.getDirectionVec().getZ());
	}

	public BlockPos offsetXNew(int xx){
		return new BlockPos(x + xx, y, z);
	}

	public BlockPos offsetYNew(int yy){
		return new BlockPos(x, y + yy, z);
	}

	public BlockPos offsetZNew(int zz){
		return new BlockPos(x, y, z + zz);
	}

	public Vec3d toVec3(){
		return new Vec3d(x, y, z);
	}

	public net.minecraft.util.math.BlockPos toBlockPos(){
		return new net.minecraft.util.math.BlockPos(x, y, z);
	}

	public IBlockState getBlockState(World world){
		return world.getBlockState(toBlockPos());
	}

	public Block getBlock(World world){
		return getBlockState(world).getBlock();
	}

	public int getMetadata(World world){
		return getBlock(world).getMetaFromState(getBlockState(world));
	}

	public TileEntity getTileEntity(World world){
		return world.getTileEntity(toBlockPos());
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt){
		x = nbt.getInteger("x");
		y = nbt.getInteger("y");
		z = nbt.getInteger("z");
	}

	public static BlockPos createFromNBT(NBTTagCompound nbt){
		return new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		BlockPos other = (BlockPos) obj;
		if(x != other.x) return false;
		if(y != other.y) return false;
		if(z != other.z) return false;
		return true;
	}

	@Override
	public String toString(){
		return "BlockPos [" + x + ", " + y + ", " + z + "]";
	}

}
