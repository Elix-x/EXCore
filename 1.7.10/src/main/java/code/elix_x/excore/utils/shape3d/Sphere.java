package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class Sphere extends Shape3D {

	protected double range;

	protected AxisAlignedBox box;

	public Sphere(World worldObj, double posX, double posY, double posZ, double range) {
		super(worldObj, posX, posY, posZ);
		this.range = range;
		this.box = new AxisAlignedBox(worldObj, posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
	}

	@Override
	public Set<BlockPos> getAffectedBlocks() {
		return getAffectedBlocks(box.getAffectedBlocks());
	}

	@Override
	public Set<Entity> getAffectedEntities() {
		return getAffectedEntities(box.getAffectedEntities());
	}

	@Override
	public Set<BlockPos> getAffectedBlocks(Set<BlockPos> set) {
		Set<BlockPos> sset = new HashSet<BlockPos>();
		double range2 = range * range;
		for(BlockPos pos : set){
			if(getPos().squareDistanceTo(pos.x, pos.y, pos.z) <= range2) sset.add(pos);
		}
		return super.getAffectedBlocks(set);
	}

	@Override
	public Set<Entity> getAffectedEntities(Set<Entity> set) {
		Set<Entity> sset = new HashSet<Entity>();
		double range2 = range * range;
		for(Entity entity : set){
			if(entity.getDistanceSq(posX, posY, posZ) <= range2) sset.add(entity);
		}
		return sset;
	}

}
