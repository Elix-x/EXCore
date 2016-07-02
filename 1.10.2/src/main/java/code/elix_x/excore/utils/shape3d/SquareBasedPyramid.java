package code.elix_x.excore.utils.shape3d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import code.elix_x.excore.utils.vecs.Vec3Utils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SquareBasedPyramid extends Shape3D {

	protected float rotYaw;
	protected float rotPitch;
	protected float rotOffset;

	protected double range;

	private SquareBasedPyramid(){
		super(0, 0, 0);
	}

	public SquareBasedPyramid(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float rotOffset, double range){
		super(posX, posY, posZ);
		this.rotYaw = rotationYaw;
		this.rotPitch = rotationPitch;
		this.rotOffset = rotOffset;
		this.range = range;
	}

	@Override
	public AxisAlignedBox getBounds(){
		return new AxisAlignedBox(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
	}

	public Vec3d[] getMainVecs(){
		return new Vec3d[]{Vec3Utils.getLookVec(rotYaw, rotPitch), Vec3Utils.getLookVec(rotYaw + rotOffset, rotPitch + rotOffset), Vec3Utils.getLookVec(rotYaw - rotOffset, rotPitch + rotOffset), Vec3Utils.getLookVec(rotYaw + rotOffset, rotPitch - rotOffset), Vec3Utils.getLookVec(rotYaw - rotOffset, rotPitch - rotOffset)};
	}

	public List<AxisAlignedBB> getMainBoxes(){
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();

		Vec3d[] vecs = new Vec3d[]{new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ)};
		Vec3d[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3d vec = vecs[i];

				Vec3d vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else{
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else{
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else{
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else{
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else{
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else{
					maxZ = Math.max(maxZ, vec.zCoord);
				}

				vecs[i] = vec;
			}

			list.add(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));

			double dist = vecs[0].distanceTo(new Vec3d(posX, posY, posZ));
			if(dist >= range){
				arrived = true;
				break;
			}
		}

		for(int i = 0; i < 5; i++){
			vecs[i] = null;
		}
		vecs = null;

		return list;
	}

	@Override
	public Set<BlockPos> getAffectedBlocks(World world){
		Set<BlockPos> blocks = new HashSet<BlockPos>();

		Vec3d[] vecs = new Vec3d[]{new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ)};
		Vec3d[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3d vec = vecs[i];

				Vec3d vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else{
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else{
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else{
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else{
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else{
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else{
					maxZ = Math.max(maxZ, vec.zCoord);
				}

				vecs[i] = vec;
			}

			blocks.addAll(new AxisAlignedBox(minX, minY, minZ, maxX, maxY, maxZ).getAffectedBlocks(world));

			double dist = vecs[0].distanceTo(new Vec3d(posX, posY, posZ));
			if(dist >= range){
				arrived = true;
				break;
			}
		}

		for(int i = 0; i < 5; i++){
			vecs[i] = null;
		}
		vecs = null;
		vec2s = null;

		return blocks;
	}

	@Override
	public <E extends Entity> Set<E> getAffectedEntities(World world, Class<E> clazz){
		Set<E> entities = new HashSet<E>();

		Set<E> set = getBounds().getAffectedEntities(world, clazz);

		Vec3d[] vecs = new Vec3d[]{new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ)};
		Vec3d[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3d vec = vecs[i];

				Vec3d vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else{
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else{
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else{
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else{
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else{
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else{
					maxZ = Math.max(maxZ, vec.zCoord);
				}

				vecs[i] = vec;
			}

			entities.addAll(new AxisAlignedBox(minX, minY, minZ, maxX, maxY, maxZ).getAffectedEntities(world, set, clazz));

			double dist = vecs[0].distanceTo(new Vec3d(posX, posY, posZ));
			if(dist >= range){
				arrived = true;
				break;
			}
		}

		for(int i = 0; i < 5; i++){
			vecs[i] = null;
		}
		vecs = null;
		vec2s = null;

		return entities;
	}

	@Override
	public boolean isInside(World world, BlockPos pos){
		return getAffectedBlocks(world).contains(pos);
	}

	@Override
	public boolean isInside(World world, Entity entity){
		return getAffectedEntities(world, entity.getClass()).contains(entity);
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(range);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(rotOffset);
		result = prime * result + Float.floatToIntBits(rotPitch);
		result = prime * result + Float.floatToIntBits(rotYaw);
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(getClass() != obj.getClass()) return false;
		SquareBasedPyramid other = (SquareBasedPyramid) obj;
		if(Double.doubleToLongBits(range) != Double.doubleToLongBits(other.range)) return false;
		if(Float.floatToIntBits(rotOffset) != Float.floatToIntBits(other.rotOffset)) return false;
		if(Float.floatToIntBits(rotPitch) != Float.floatToIntBits(other.rotPitch)) return false;
		if(Float.floatToIntBits(rotYaw) != Float.floatToIntBits(other.rotYaw)) return false;
		return true;
	}

}
