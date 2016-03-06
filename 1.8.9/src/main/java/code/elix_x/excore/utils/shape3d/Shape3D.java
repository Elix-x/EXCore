package code.elix_x.excore.utils.shape3d;

import java.util.HashSet;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
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

	public Vec3 getPos(){
		return new Vec3(posX, posY, posZ);
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

	public boolean isInside(World world, Vec3 vec){
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
		return isInside(world, new AxisAlignedBox(entity.getEntityBoundingBox()));
	}

	public boolean intersectsWith(World world, Entity entity){
		return intersectsWith(world, new AxisAlignedBox(entity.getEntityBoundingBox()));
	}

}
