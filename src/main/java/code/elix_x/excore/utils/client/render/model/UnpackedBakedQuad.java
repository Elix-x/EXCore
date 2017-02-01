package code.elix_x.excore.utils.client.render.model;

import code.elix_x.excore.utils.client.render.vertex.DefaultUnpackedVertices;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;

public class UnpackedBakedQuad {

	private DefaultUnpackedVertices vertices;
	private int tintIndex;
	private EnumFacing face;
	private TextureAtlasSprite sprite;
	private VertexFormat format;
	private boolean applyDiffuseLighting;

	public UnpackedBakedQuad(DefaultUnpackedVertices vertices, int tintIndex, EnumFacing face, TextureAtlasSprite sprite, VertexFormat format, boolean applyDiffuseLighting){
		this.vertices = vertices;
		this.tintIndex = tintIndex;
		this.face = face;
		this.sprite = sprite;
		this.format = format;
		this.applyDiffuseLighting = applyDiffuseLighting;
	}

	public DefaultUnpackedVertices getVertices(){
		return vertices;
	}

	public void setVertices(DefaultUnpackedVertices vertices){
		this.vertices = vertices;
	}

	public int getTintIndex(){
		return tintIndex;
	}

	public void setTintIndex(int tintIndex){
		this.tintIndex = tintIndex;
	}

	public EnumFacing getFace(){
		return face;
	}

	public void setFace(EnumFacing face){
		this.face = face;
	}

	public TextureAtlasSprite getSprite(){
		return sprite;
	}

	public void setSprite(TextureAtlasSprite sprite){
		this.sprite = sprite;
	}

	public VertexFormat getFormat(){
		return format;
	}

	public void setFormat(VertexFormat format){
		this.format = format;
	}

	public boolean isApplyDiffuseLighting(){
		return applyDiffuseLighting;
	}

	public void setApplyDiffuseLighting(boolean applyDiffuseLighting){
		this.applyDiffuseLighting = applyDiffuseLighting;
	}

}
