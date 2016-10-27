package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Sphere extends Shape3D {

	protected double radius;

	protected AxisAlignedBox box;

	private Sphere(){
		super(0, 0, 0);
	}

	public Sphere(double posX, double posY, double posZ, double range){
		super(posX, posY, posZ);
		this.radius = range;
		this.box = new AxisAlignedBox(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
	}

	@Override
	public AxisAlignedBox getBounds(){
		return box;
	}

	@Override
	public Set<BlockPos> getAffectedBlocks(World world){
		return getAffectedBlocks(world, box.getAffectedBlocks(world));
	}

	@Override
	public <E extends Entity> Set<E> getAffectedEntities(World world, Class<E> clazz){
		return getAffectedEntities(world, box.getAffectedEntities(world, clazz), clazz);
	}

	@Override
	public Set<BlockPos> getAffectedBlocks(World world, Set<BlockPos> set){
		Set<BlockPos> sset = new HashSet<BlockPos>();
		double range2 = radius * radius;
		for(BlockPos pos : set){
			if(getPos().squareDistanceTo(pos.toVec3()) <= range2) sset.add(pos);
		}
		return sset;
	}

	@Override
	public <E extends Entity> Set<E> getAffectedEntities(World world, Set<E> set, Class<E> clazz){
		Set<E> sset = new HashSet<E>();
		double range2 = radius * radius;
		for(E entity : set){
			if(entity.getDistanceSq(posX, posY, posZ) <= range2) sset.add(entity);
		}
		return sset;
	}

	@Override
	public boolean isInside(World world, Vec3d vec){
		return getPos().squareDistanceTo(vec) <= radius * radius;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(getClass() != obj.getClass()) return false;
		Sphere other = (Sphere) obj;
		if(Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius)) return false;
		return true;
	}

}
