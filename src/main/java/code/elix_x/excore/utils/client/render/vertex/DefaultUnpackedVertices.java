package code.elix_x.excore.utils.client.render.vertex;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.vertex.VertexFormat;

public class DefaultUnpackedVertices {

	private List<DefaultUnpackedVertex> vertices;

	public DefaultUnpackedVertices(DefaultUnpackedVertex... vertices){
		this.vertices = Lists.newArrayList(vertices);
	}

	public DefaultUnpackedVertices(List<DefaultUnpackedVertex> vertices){
		this.vertices = Lists.newArrayList(vertices);
	}

	public DefaultUnpackedVertices(VertexFormat format, List<PackedVertex> vertices){
		this(Lists.transform(vertices, vertex -> vertex.unpack()));
	}

	public List<DefaultUnpackedVertex> getVertices(){
		return vertices;
	}

	public void setVertices(List<DefaultUnpackedVertex> vertices){
		this.vertices = vertices;
	}

	public PackedVertices pack(int mode, VertexFormat format){
		return new PackedVertices(mode, format, Lists.transform(vertices, vertex -> vertex.pack(format)));
	}

}
