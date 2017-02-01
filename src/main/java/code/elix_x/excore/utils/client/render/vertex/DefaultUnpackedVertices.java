package code.elix_x.excore.utils.client.render.vertex;

import java.util.List;

import com.google.common.collect.Lists;

public class DefaultUnpackedVertices {

	private List<DefaultUnpackedVertex> vertices;

	public DefaultUnpackedVertices(DefaultUnpackedVertex... vertices){
		this.vertices = Lists.newArrayList(vertices);
	}

	public DefaultUnpackedVertices(List<DefaultUnpackedVertex> vertices){
		this.vertices = Lists.newArrayList(vertices);
	}

	public List<DefaultUnpackedVertex> getVertices(){
		return vertices;
	}

	public void setVertices(List<DefaultUnpackedVertex> vertices){
		this.vertices = vertices;
	}

}
