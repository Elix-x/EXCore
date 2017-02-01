package code.elix_x.excore.utils.client.render.model;

import org.lwjgl.opengl.GL11;

import code.elix_x.excomms.reflection.ReflectionHelper.AClass;
import code.elix_x.excomms.reflection.ReflectionHelper.AField;
import code.elix_x.excore.utils.client.render.vertex.DefaultUnpackedVertices;
import code.elix_x.excore.utils.client.render.vertex.PackedVertices;
import net.minecraft.client.renderer.block.model.BakedQuad;
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

	public UnpackedBakedQuad(PackedVertices vertices, int tintIndex, EnumFacing face, TextureAtlasSprite sprite, VertexFormat format, boolean applyDiffuseLighting){
		this(vertices.unpack(), tintIndex, face, sprite, format, applyDiffuseLighting);
	}

	public UnpackedBakedQuad(float[][][] vertexData, int tintIndex, EnumFacing face, TextureAtlasSprite sprite, VertexFormat format, boolean applyDiffuseLighting){
		this(new PackedVertices(GL11.GL_QUADS, format, vertexData), tintIndex, face, sprite, format, applyDiffuseLighting);
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

	public BakedQuad pack(){
		return new net.minecraftforge.client.model.pipeline.UnpackedBakedQuad(vertices.pack(GL11.GL_QUADS, format).getData(), tintIndex, face, sprite, applyDiffuseLighting, format);
	}

	private static final AField<net.minecraftforge.client.model.pipeline.UnpackedBakedQuad, float[][][]> unpackedData = new AClass<>(net.minecraftforge.client.model.pipeline.UnpackedBakedQuad.class).<float[][][]> getDeclaredField("unpackedData").setAccessible(true);

	public static UnpackedBakedQuad unpack(net.minecraftforge.client.model.pipeline.UnpackedBakedQuad quad){
		return new UnpackedBakedQuad(unpackedData.get(quad), quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.getFormat(), quad.shouldApplyDiffuseLighting());
	}

}
