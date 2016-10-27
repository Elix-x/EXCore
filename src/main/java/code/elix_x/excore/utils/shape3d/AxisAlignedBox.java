package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import code.elix_x.excore.utils.math.AdvancedMathUtils;
import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AxisAlignedBox extends Shape3D {

	protected double minX;
	protected double minY;
	protected double minZ;
	protected double maxX;
	protected double maxY;
	protected double maxZ;

	private AxisAlignedBox(){
		super(0, 0, 0);
	}

	public AxisAlignedBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
		super(AdvancedMathUtils.average(minX, maxX), AdvancedMathUtils.average(minY, maxY), AdvancedMathUtils.average(minZ, maxZ));
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public AxisAlignedBox(AxisAlignedBB box){
		this(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
	}

	public AxisAlignedBB toAxisAlignedBB(){
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public AxisAlignedBox getBounds(){
		return this;
	}

	@Override
	public Set<BlockPos> getAffectedBlocks(World world){
		Set<BlockPos> set = new HashSet<BlockPos>();
		for(int i = (int) minX; i <= maxX; i++){
			for(int j = (int) minY; j <= maxY; j++){
				for(int k = (int) minZ; k <= maxZ; k++){
					set.add(new BlockPos(i, j, k));
				}
			}
		}
		return set;
	}

	@Override
	public <E extends Entity> Set<E> getAffectedEntities(World world, Class<E> clazz){
		return Sets.newHashSet(world.getEntitiesWithinAABB(clazz, toAxisAlignedBB()));
	}

	@Override
	public boolean isInside(World world, Vec3d vec){
		return toAxisAlignedBB().isVecInside(vec);
	}

	@Override
	public boolean isInside(World world, Shape3D shape){
		AxisAlignedBox box = shape.getBounds();
		return minX < box.minX && maxX > box.maxX && minY < box.minY && maxY > box.maxY && minZ < box.minZ && maxZ > box.maxZ;
	}

	@Override
	public boolean intersectsWith(World world, Shape3D shape){
		return toAxisAlignedBB().intersectsWith(shape.getBounds().toAxisAlignedBB());
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(maxX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(getClass() != obj.getClass()) return false;
		AxisAlignedBox other = (AxisAlignedBox) obj;
		if(Double.doubleToLongBits(maxX) != Double.doubleToLongBits(other.maxX)) return false;
		if(Double.doubleToLongBits(maxY) != Double.doubleToLongBits(other.maxY)) return false;
		if(Double.doubleToLongBits(maxZ) != Double.doubleToLongBits(other.maxZ)) return false;
		if(Double.doubleToLongBits(minX) != Double.doubleToLongBits(other.minX)) return false;
		if(Double.doubleToLongBits(minY) != Double.doubleToLongBits(other.minY)) return false;
		if(Double.doubleToLongBits(minZ) != Double.doubleToLongBits(other.minZ)) return false;
		return true;
	}

}
