package code.elix_x.excore.utils.client.render.model;

import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class UnpackedSimpleBakedModel {

	private List<UnpackedBakedQuad> generalQuads;
	private Map<EnumFacing, List<UnpackedBakedQuad>> faceQuads;
	private boolean ambientOcclusion;
	private boolean gui3d;
	private TextureAtlasSprite texture;
	private ItemCameraTransforms cameraTransforms;
	private ItemOverrideList itemOverrideList;

	public UnpackedSimpleBakedModel(List<UnpackedBakedQuad> generalQuads, Map<EnumFacing, List<UnpackedBakedQuad>> faceQuads, boolean ambientOcclusion, boolean gui3d, TextureAtlasSprite texture, ItemCameraTransforms cameraTransforms, ItemOverrideList itemOverrideList){
		this.generalQuads = generalQuads;
		this.faceQuads = faceQuads;
		this.ambientOcclusion = ambientOcclusion;
		this.gui3d = gui3d;
		this.texture = texture;
		this.cameraTransforms = cameraTransforms;
		this.itemOverrideList = itemOverrideList;
	}

	public List<UnpackedBakedQuad> getGeneralQuads(){
		return generalQuads;
	}

	public void setGeneralQuads(List<UnpackedBakedQuad> generalQuads){
		this.generalQuads = generalQuads;
	}

	public Map<EnumFacing, List<UnpackedBakedQuad>> getFaceQuads(){
		return faceQuads;
	}

	public void setFaceQuads(Map<EnumFacing, List<UnpackedBakedQuad>> faceQuads){
		this.faceQuads = faceQuads;
	}

	public boolean isAmbientOcclusion(){
		return ambientOcclusion;
	}

	public void setAmbientOcclusion(boolean ambientOcclusion){
		this.ambientOcclusion = ambientOcclusion;
	}

	public boolean isGui3d(){
		return gui3d;
	}

	public void setGui3d(boolean gui3d){
		this.gui3d = gui3d;
	}

	public TextureAtlasSprite getTexture(){
		return texture;
	}

	public void setTexture(TextureAtlasSprite texture){
		this.texture = texture;
	}

	public ItemCameraTransforms getCameraTransforms(){
		return cameraTransforms;
	}

	public void setCameraTransforms(ItemCameraTransforms cameraTransforms){
		this.cameraTransforms = cameraTransforms;
	}

	public ItemOverrideList getItemOverrideList(){
		return itemOverrideList;
	}

	public void setItemOverrideList(ItemOverrideList itemOverrideList){
		this.itemOverrideList = itemOverrideList;
	}

}
