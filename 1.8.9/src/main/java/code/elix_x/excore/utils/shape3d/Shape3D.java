package code.elix_x.excore.utils.shape3d;

import java.util.Set;

import com.google.common.collect.Sets;

import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class Shape3D {

	protected World worldObj;

	protected double posX;
	protected double posY;
	protected double posZ;

	public Shape3D(World worldObj, double posX, double posY, double posZ) {
		this.worldObj = worldObj;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public Vec3 getPos() {
		return new Vec3(posX, posY, posZ);
	}

	public abstract Set<BlockPos> getAffectedBlocks();

	public abstract Set<Entity> getAffectedEntities();

	public Set<BlockPos> getAffectedBlocks(Set<BlockPos> set) {
		return Sets.intersection(set, getAffectedBlocks());
	}

	public Set<Entity> getAffectedEntities(Set<Entity> set) {
		return Sets.intersection(set, getAffectedEntities());
	}

}
