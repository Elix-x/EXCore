package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import code.elix_x.excore.utils.math.AdvancedMathUtils;
import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class AxisAlignedBox extends Shape3D {

	protected AxisAlignedBB box;

	public AxisAlignedBox(World worldObj, AxisAlignedBB box) {
		super(worldObj, AdvancedMathUtils.average(box.minX, box.maxX), AdvancedMathUtils.average(box.minY, box.maxY), AdvancedMathUtils.average(box.minZ, box.maxZ));
		this.box = box;
	}

	public AxisAlignedBox(World worldObj, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this(worldObj, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
	}

	@Override
	public Set<BlockPos> getAffectedBlocks() {
		Set<BlockPos> set = new HashSet<BlockPos>();
		for(int i = (int) box.minX; i <= box.maxX; i++){
			for(int j = (int) box.minY; j <= box.maxY; j++){
				for(int k = (int) box.minZ; k <= box.maxZ; k++){
					set.add(new BlockPos(i, j, k));
				}
			}
		}
		return set;
	}

	@Override
	public Set<Entity> getAffectedEntities() {
		return Sets.newHashSet(worldObj.getEntitiesWithinAABB(Entity.class, box));
	}

	@Override
	public Set<BlockPos> getAffectedBlocks(Set<BlockPos> set) {
		Set<BlockPos> sset = new HashSet<BlockPos>();
		for(BlockPos pos : set){
			if(box.isVecInside(pos.toVec3())) sset.add(pos);
		}
		return sset;
	}

	@Override
	public Set<Entity> getAffectedEntities(Set<Entity> set) {
		Set<Entity> sset = new HashSet<Entity>();
		for(Entity entity : set){
			if(box.intersectsWith(entity.boundingBox)) sset.add(entity);
		}
		return sset;
	}

}
