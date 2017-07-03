package code.elix_x.excore.utils.client.render.pipeline.vertex;

import code.elix_x.excomms.pipeline.PipelineElement;
import code.elix_x.excore.utils.client.render.model.vertex.DefaultUnpackedVertex;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.util.vector.Matrix4f;


public class VertexMatrixTransformer implements PipelineElement<DefaultUnpackedVertex, DefaultUnpackedVertex> {

	private javax.vecmath.Matrix4f matrix;

	public VertexMatrixTransformer(Matrix4f matrix){
		setMatrix(matrix);
	}

	public Matrix4f getMatrix(){
		return TRSRTransformation.toLwjgl(matrix);
	}

	public void setMatrix(Matrix4f matrix){
		this.matrix = TRSRTransformation.toVecmath(matrix);
	}

	@Override
	public DefaultUnpackedVertex pipe(DefaultUnpackedVertex in){
		matrix.transform(TRSRTransformation.toVecmath(in.getPos()));
		//TODO Normals
		// TRSRTransformation.toVecmath(matrix)
		return in;
	}

}
