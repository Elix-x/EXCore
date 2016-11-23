package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class Shape3D {

	protected double posX;
	protected double posY;
	protected double posZ;

	public Shape3D(double posX, double posY, double posZ){
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public Vec3d getPos(){
		return new Vec3d(posX, posY, posZ);
	}

	public abstract AxisAlignedBox getBounds();

	public abstract Set<BlockPos> getAffectedBlocks(World world);

	public abstract <E extends Entity> Set<E> getAffectedEntities(World world, Class<E> clazz);

	public Set<BlockPos> getAffectedBlocks(World world, Set<BlockPos> set){
		Set<BlockPos> sset = new HashSet<BlockPos>();
		for(BlockPos pos : set){
			if(isInside(world, pos)) sset.add(pos);
		}
		return sset;
	}

	public <E extends Entity> Set<E> getAffectedEntities(World world, Set<E> set, Class<E> clazz){
		Set<E> sset = new HashSet<E>();
		for(E entity : set){
			if(intersectsWith(world, entity)) sset.add(entity);
		}
		return sset;
	}

	public boolean isInside(World world, Vec3d vec){
		return getBounds().isInside(world, vec);
	}

	public boolean isInside(World world, Shape3D shape){
		return getBounds().isInside(world, shape);
	}

	public boolean intersectsWith(World world, Shape3D shape){
		return getBounds().intersectsWith(world, shape);
	}

	public boolean isInside(World world, BlockPos pos){
		return isInside(world, pos.toVec3());
	}

	public boolean isInside(World world, Entity entity){
		return entity.getEntityBoundingBox() != null ? isInside(world, new AxisAlignedBox(entity.getEntityBoundingBox())) : isInside(world, new Vec3d(entity.posX, entity.posY, entity.posZ));
	}

	public boolean intersectsWith(World world, Entity entity){
		return entity.getEntityBoundingBox() != null ? intersectsWith(world, new AxisAlignedBox(entity.getEntityBoundingBox())) : isInside(world, new Vec3d(entity.posX, entity.posY, entity.posZ));
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(posX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(posY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(posZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Shape3D other = (Shape3D) obj;
		if(Double.doubleToLongBits(posX) != Double.doubleToLongBits(other.posX)) return false;
		if(Double.doubleToLongBits(posY) != Double.doubleToLongBits(other.posY)) return false;
		if(Double.doubleToLongBits(posZ) != Double.doubleToLongBits(other.posZ)) return false;
		return true;
	}

}
