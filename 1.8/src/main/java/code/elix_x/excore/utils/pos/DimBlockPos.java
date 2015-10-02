package code.elix_x.excore.utils.pos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DimBlockPos extends BlockPos{

	public int dimId;

	public DimBlockPos(int x, int y, int z, int dimId) {
		super(x, y, z);
		this.dimId = dimId;
	}
	
	public DimBlockPos(net.minecraft.util.BlockPos pos, int dimId) {
		this(pos.getX(), pos.getY(), pos.getZ(), dimId);
	}
	
	public DimBlockPos(TileEntity te) {
		this(te.getPos(), te.getWorld().provider.getDimensionId());
	}

	public int getDimId() {
		return dimId;
	}

	public void setDimId(int dimId) {
		this.dimId = dimId;
	}
	
	public TileEntity getTileEntity(){
		return MinecraftServer.getServer().worldServerForDimension(dimId).getTileEntity(toBlockPos());
	}
	
	public IBlockState getBlockState(){
		return super.getBlockState(MinecraftServer.getServer().worldServerForDimension(dimId));
	}
	
	public Block getBlock(){
		return super.getBlock(MinecraftServer.getServer().worldServerForDimension(dimId));
	}
	
	public int getMetadata(World world){
		return super.getMetadata(MinecraftServer.getServer().worldServerForDimension(dimId));
	}
	
	public TileEntity getTileEntity(World world){
		return world.getTileEntity(toBlockPos());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt = super.writeToNBT(nbt);
		nbt.setInteger("dimId", dimId);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		dimId = nbt.getInteger("dimId");
	}
	
	public static DimBlockPos createFromNBT(NBTTagCompound nbt){
		return new DimBlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getInteger("dimId"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dimId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DimBlockPos other = (DimBlockPos) obj;
		if (dimId != other.dimId)
			return false;
		return true;
	}
	
	
	
}
