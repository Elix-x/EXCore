package code.elix_x.excore.utils.vecs;

import java.util.Random;

import code.elix_x.excore.utils.arrays.SpecialArrayUtils;
import code.elix_x.excore.utils.random.AdvancedRandomUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Vec3Utils {

	public static Vec3d getLookVec(float rotationYaw, float rotationPitch){
		return getLook(rotationYaw, rotationPitch, 1.0f, 0, 0);
	}

	public static Vec3d getLook(float rotationYaw, float rotationPitch, float f, float prevRotationYaw, float prevRotationPitch){
		float f1;
		float f2;
		float f3;
		float f4;

		if(f == 1.0F){
			f1 = MathHelper.cos(-rotationYaw * 0.017453292F - (float) Math.PI);
			f2 = MathHelper.sin(-rotationYaw * 0.017453292F - (float) Math.PI);
			f3 = -MathHelper.cos(-rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-rotationPitch * 0.017453292F);
			return new Vec3d((double) (f2 * f3), (double) f4, (double) (f1 * f3));
		} else{
			f1 = prevRotationPitch + (rotationPitch - prevRotationPitch) * f;
			f2 = prevRotationYaw + (rotationYaw - prevRotationYaw) * f;
			f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
			f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			return new Vec3d((double) (f4 * f5), (double) f6, (double) (f3 * f5));
		}
	}

	public static Vec3d getRandomVecBetween(Random random, Vec3d... vecs){
		double[] xs = getAllX(vecs);
		double[] ys = getAllY(vecs);
		double[] zs = getAllZ(vecs);
		double minX = SpecialArrayUtils.min(xs);
		double maxX = SpecialArrayUtils.max(xs);
		double minY = SpecialArrayUtils.min(ys);
		double maxY = SpecialArrayUtils.max(ys);
		double minZ = SpecialArrayUtils.min(zs);
		double maxZ = SpecialArrayUtils.max(zs);
		return new Vec3d(AdvancedRandomUtils.nextDouble(random, minX, maxX), AdvancedRandomUtils.nextDouble(random, minY, maxY), AdvancedRandomUtils.nextDouble(random, minZ, maxZ));
	}

	public static double[] getAllX(Vec3d... vecs){
		double[] xs = new double[vecs.length];
		for(int i = 0; i < vecs.length; i++){
			xs[i] = vecs[i].xCoord;
		}
		return xs;
	}

	public static double[] getAllY(Vec3d... vecs){
		double[] ys = new double[vecs.length];
		for(int i = 0; i < vecs.length; i++){
			ys[i] = vecs[i].yCoord;
		}
		return ys;
	}

	public static double[] getAllZ(Vec3d... vecs){
		double[] zs = new double[vecs.length];
		for(int i = 0; i < vecs.length; i++){
			zs[i] = vecs[i].zCoord;
		}
		return zs;
	}

}
