package code.elix_x.excore.utils.client.render.vertex;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import code.elix_x.excomms.color.RGBA;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.util.math.Vec3i;

public class DefaultUnpackedVertex {

	private Vector3f pos;
	private RGBA color;
	private Vector2f texture;
	/**
	 * Note: Only {@linkplain Vec3i#x} and {@linkplain Vec3i#y} are used, and as valid shorts.
	 */
	private Vec3i lightmap;
	private Vec3i normal;
	private Map<VertexFormatElement, float[]> unknown = new HashMap<>();

	public DefaultUnpackedVertex(Vector3f pos, RGBA color, Vector2f texture, Vec3i lightmap, Vec3i normal){
		this.pos = pos;
		this.color = color;
		this.texture = texture;
		this.lightmap = lightmap;
		this.normal = normal;
	}

	public Vector3f getPos(){
		return pos;
	}

	public void setPos(Vector3f pos){
		this.pos = pos;
	}

	public RGBA getColor(){
		return color;
	}

	public void setColor(RGBA color){
		this.color = color;
	}

	public Vector2f getTexture(){
		return texture;
	}

	public void setTexture(Vector2f texture){
		this.texture = texture;
	}

	public Vec3i getLightmap(){
		return lightmap;
	}

	public void setLightmap(Vec3i lightmap){
		this.lightmap = lightmap;
	}

	public Vec3i getNormal(){
		return normal;
	}

	public void setNormal(Vec3i normal){
		this.normal = normal;
	}

	public Map<VertexFormatElement, float[]> getUnknown(){
		return unknown;
	}

	public void setUnknown(Map<VertexFormatElement, float[]> unknown){
		this.unknown = unknown;
	}

	public float[] getUnknown(VertexFormatElement element){
		return unknown.get(element);
	}

	public void setUnknown(VertexFormatElement element, float[] data){
		unknown.put(element, data);
	}
}
