package code.elix_x.excore.utils.client.render.vertex;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.vertex.VertexFormat;

public class PackedVertices {

	private final int mode;
	private final VertexFormat format;
	private final ImmutableList<PackedVertex> vertices;

	public PackedVertices(int mode, VertexFormat format, PackedVertex... vertices){
		this.mode = mode;
		this.format = format;
		this.vertices = ImmutableList.copyOf(vertices);
	}

	public PackedVertices(int mode, VertexFormat format, List<PackedVertex> vertices){
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

	public List<PackedVertex> getVertices(){
		return vertices;
	}

	public DefaultUnpackedVertices unpack(){
		return new DefaultUnpackedVertices(format, vertices);
	}

}
