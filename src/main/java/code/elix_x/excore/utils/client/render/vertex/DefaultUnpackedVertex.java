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

	public DefaultUnpackedVertex(VertexFormat format, float[][] data){
		elements: for(int i = 0; i < data.length; i++){
			VertexFormatElement element = format.getElement(i);
			float[] edata = data[i];
			switch(element.getUsage()){
				case POSITION:
					if(element.getType() == EnumType.FLOAT && element.getElementCount() == 3){
						pos = new Vector3f(edata[0], edata[1], edata[2]);
						continue elements;
					} else break;
				case COLOR:
					if(element.getType() == EnumType.UBYTE && element.getElementCount() == 4){
						color = new RGBA((int) edata[0], (int) edata[0], (int) edata[0], (int) edata[0]);
						continue elements;
					} else break;
				case UV:
					if(element.getType() == EnumType.FLOAT && element.getElementCount() == 2){
						texture = new Vector2f(edata[0], edata[1]);
						continue elements;
					} else if(element.getType() == EnumType.SHORT && element.getElementCount() == 2){
						lightmap = new Vec3i((short) edata[0], (short) edata[1], 0);
						continue elements;
					} else break;
				case NORMAL:
					if(element.getType() == EnumType.BYTE && element.getElementCount() == 3){
						normal = new Vec3i((byte) edata[0], (byte) edata[1], (byte) edata[2]);
						continue elements;
					} else break;
				case PADDING:
					continue elements;
				default:
					break;
			}
			unknown.put(element, edata);
		}
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

	public PackedVertex pack(VertexFormat format){
		float[][] data = new float[format.getElementCount()][];
		elements: for(int i = 0; i < data.length; i++){
			VertexFormatElement element = format.getElement(i);
			float[] edata = data[i] = new float[element.getElementCount()];
			switch(element.getUsage()){
				case POSITION:
					if(element.getType() == EnumType.FLOAT && element.getElementCount() == 3){
						edata[0] = pos.x;
						edata[1] = pos.y;
						edata[2] = pos.z;
						continue elements;
					} else break;
				case COLOR:
					if(element.getType() == EnumType.UBYTE && element.getElementCount() == 4){
						edata[0] = color.getRI();
						edata[1] = color.getGI();
						edata[2] = color.getBI();
						edata[3] = color.getAI();
						continue elements;
					} else break;
				case UV:
					if(element.getType() == EnumType.FLOAT && element.getElementCount() == 2){
						edata[0] = texture.x;
						edata[1] = texture.y;
						continue elements;
					} else if(element.getType() == EnumType.SHORT && element.getElementCount() == 2){
						edata[0] = (short) lightmap.getX();
						edata[1] = (short) lightmap.getY();
						continue elements;
					} else break;
				case NORMAL:
					if(element.getType() == EnumType.BYTE && element.getElementCount() == 3){
						edata[0] = (byte) normal.getX();
						edata[1] = (byte) normal.getY();
						edata[2] = (byte) normal.getZ();
						continue elements;
					} else break;
				case PADDING:
					continue elements;
				default:
					break;
			}
			if(unknown.containsKey(element)) System.arraycopy(unknown.get(element), 0, edata, 0, edata.length);
		}
		return new PackedVertex(format, data);
	}

}
