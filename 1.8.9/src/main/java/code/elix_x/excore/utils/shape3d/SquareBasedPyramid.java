package code.elix_x.excore.utils.shape3d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import code.elix_x.excore.utils.pos.BlockPos;
import code.elix_x.excore.utils.vecs.Vec3Utils;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SquareBasedPyramid extends Shape3D {

	protected float rotYaw;
	protected float rotPitch;
	protected float rotOffset;

	protected double range;

	public SquareBasedPyramid(World worldObj, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float rotOffset, double range) {
		super(worldObj, posX, posY, posZ);
		this.rotYaw = rotationYaw;
		this.rotPitch = rotationPitch;
		this.rotOffset = rotOffset;
		this.range = range;
	}

	public Vec3[] getMainVecs() {
		return new Vec3[]{Vec3Utils.getLookVec(rotYaw, rotPitch), Vec3Utils.getLookVec(rotYaw + rotOffset, rotPitch + rotOffset), Vec3Utils.getLookVec(rotYaw - rotOffset, rotPitch + rotOffset), Vec3Utils.getLookVec(rotYaw + rotOffset, rotPitch - rotOffset), Vec3Utils.getLookVec(rotYaw - rotOffset, rotPitch - rotOffset)};
	}

	public List<AxisAlignedBB> getMainBoxes() {
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();

		Vec3[] vecs = new Vec3[]{new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ)};
		Vec3[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3 vec = vecs[i];

				Vec3 vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else {
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else {
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else {
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else {
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else {
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else {
					maxZ = Math.max(maxZ, vec.zCoord);
				}


				vecs[i] = vec;
			}

			list.add(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));

			double dist = vecs[0].distanceTo(new Vec3(posX, posY, posZ));
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

	public Set<BlockPos> getAffectedBlocks(){
		Set<BlockPos> blocks = new HashSet<BlockPos>();

		Vec3[] vecs = new Vec3[]{new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ)};
		Vec3[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3 vec = vecs[i];

				Vec3 vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else {
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else {
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else {
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else {
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else {
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else {
					maxZ = Math.max(maxZ, vec.zCoord);
				}


				vecs[i] = vec;
			}

			blocks.addAll(new AxisAlignedBox(worldObj, minX, minY, minZ, maxX, maxY, maxZ).getAffectedBlocks());

			double dist = vecs[0].distanceTo(new Vec3(posX, posY, posZ));
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

	public Set<Entity> getAffectedEntities(){
		Set<Entity> entities = new HashSet<Entity>();

		Set<Entity> set = new AxisAlignedBox(worldObj, posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range).getAffectedEntities();

		Vec3[] vecs = new Vec3[]{new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ), new Vec3(posX, posY, posZ)};
		Vec3[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3 vec = vecs[i];

				Vec3 vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else {
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else {
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else {
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else {
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else {
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else {
					maxZ = Math.max(maxZ, vec.zCoord);
				}


				vecs[i] = vec;
			}

			entities.addAll(new AxisAlignedBox(worldObj, minX, minY, minZ, maxX, maxY, maxZ).getAffectedEntities(set));

			double dist = vecs[0].distanceTo(new Vec3(posX, posY, posZ));
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

}