package code.elix_x.excore.utils.client.render.pipeline;

import code.elix_x.excomms.pipeline.PipelineElement;
import code.elix_x.excore.utils.client.render.model.UnpackedBakedQuad;
import code.elix_x.excore.utils.client.render.model.vertex.DefaultUnpackedVertices;

public class BakedQuadVerticesPipelineWrapper implements PipelineElement<UnpackedBakedQuad, UnpackedBakedQuad> {

	private final PipelineElement<DefaultUnpackedVertices, DefaultUnpackedVertices> pipeline;

	public BakedQuadVerticesPipelineWrapper(PipelineElement<DefaultUnpackedVertices, DefaultUnpackedVertices> pipeline){
		this.pipeline = pipeline;
	}

	@Override
	public UnpackedBakedQuad pipe(UnpackedBakedQuad in){
		in.setVertices(pipeline.pipe(in.getVertices()));
		return in;
	}

}
