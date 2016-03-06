package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Sphere extends Shape3D {

	protected double range;

	protected AxisAlignedBox box;

	private Sphere(){
		super(0, 0, 0);
	}

	public Sphere(double posX, double posY, double posZ, double range) {
		super(posX, posY, posZ);
		this.range = range;
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
		double range2 = range * range;
		for(BlockPos pos : set){
			if(getPos().squareDistanceTo(pos.toVec3()) <= range2) sset.add(pos);
		}
		return sset;
	}

	@Override
	public <E extends Entity> Set<E> getAffectedEntities(World world, Set<E> set, Class<E> clazz){
		Set<E> sset = new HashSet<E>();
		double range2 = range * range;
		for(E entity : set){
			if(entity.getDistanceSq(posX, posY, posZ) <= range2) sset.add(entity);
		}
		return sset;
	}

	@Override
	public boolean isInside(World world, Vec3 vec){
		return getPos().squareDistanceTo(vec) <= range * range;
	}

}
