
package code.elix_x.excore.utils.client.render.pipeline_old;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;

public class ParentQuads implements BakingPipelineElement<Void, BakedQuad> {

	@Override
	public List<BakedQuad> pipe(List<Void> elements, IBakedModel parent, IBlockState state, EnumFacing side, long rand){
		return parent.getQuads(state, side, rand);
	}

}
