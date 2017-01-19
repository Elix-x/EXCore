package code.elix_x.excore.utils.client.render.vertex;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.vertex.VertexFormat;

public class Vertices {

	private final int mode;
	private final VertexFormat format;
	private final ImmutableList<Vertex> vertices;

	public Vertices(int mode, VertexFormat format, Vertex... vertices){
		this.mode = mode;
		this.format = format;
		this.vertices = ImmutableList.copyOf(vertices);
	}

	public Vertices(int mode, VertexFormat format, List<Vertex> vertices){
		this.mode = mode;
		this.format = format;
		this.vertices = ImmutableList.copyOf(vertices);
	}

	public int getMode(){
		return mode;
	}

	public VertexFormat getFormat(){
		return format;
	}

	public List<Vertex> getVertices(){
		return vertices;
	}

}
