package code.elix_x.excore.utils.client.render.pipeline;

import java.util.Optional;
import java.util.function.Function;

import code.elix_x.excomms.pipeline.PipelineElement;
import code.elix_x.excore.utils.client.render.model.UnpackedSimpleBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SimpleBakedModelDataModifierPipelineElement implements PipelineElement<UnpackedSimpleBakedModel, UnpackedSimpleBakedModel> {

	public static SimpleBakedModelDataModifierPipelineElement ambientOcclusion(Function<Boolean, Boolean> ambientOcclusion){
		return new SimpleBakedModelDataModifierPipelineElement(ambientOcclusion, null, null, null, null);
	}

	public static SimpleBakedModelDataModifierPipelineElement gui3d(Function<Boolean, Boolean> gui3d){
		return new SimpleBakedModelDataModifierPipelineElement(null, gui3d, null, null, null);
	}

	public static SimpleBakedModelDataModifierPipelineElement texture(Function<TextureAtlasSprite, TextureAtlasSprite> texture){
		return new SimpleBakedModelDataModifierPipelineElement(null, null, texture, null, null);
	}

	public static SimpleBakedModelDataModifierPipelineElement cameraTransforms(Function<ItemCameraTransforms, ItemCameraTransforms> cameraTransforms){
		return new SimpleBakedModelDataModifierPipelineElement(null, null, null, cameraTransforms, null);
	}

	public static SimpleBakedModelDataModifierPipelineElement itemOverrideList(Function<ItemOverrideList, ItemOverrideList> itemOverrideList){
		return new SimpleBakedModelDataModifierPipelineElement(null, null, null, null, itemOverrideList);
	}

	private final Optional<Function<Boolean, Boolean>> ambientOcclusion;
	private final Optional<Function<Boolean, Boolean>> gui3d;
	private final Optional<Function<TextureAtlasSprite, TextureAtlasSprite>> texture;
	private final Optional<Function<ItemCameraTransforms, ItemCameraTransforms>> cameraTransforms;
	private final Optional<Function<ItemOverrideList, ItemOverrideList>> itemOverrideList;

	public SimpleBakedModelDataModifierPipelineElement(Optional<Function<Boolean, Boolean>> ambientOcclusion, Optional<Function<Boolean, Boolean>> gui3d, Optional<Function<TextureAtlasSprite, TextureAtlasSprite>> texture, Optional<Function<ItemCameraTransforms, ItemCameraTransforms>> cameraTransforms, Optional<Function<ItemOverrideList, ItemOverrideList>> itemOverrideList){
		this.ambientOcclusion = ambientOcclusion;
		this.gui3d = gui3d;
		this.texture = texture;
		this.cameraTransforms = cameraTransforms;
		this.itemOverrideList = itemOverrideList;
	}

	public SimpleBakedModelDataModifierPipelineElement(Function<Boolean, Boolean> ambientOcclusion, Function<Boolean, Boolean> gui3d, Function<TextureAtlasSprite, TextureAtlasSprite> texture, Function<ItemCameraTransforms, ItemCameraTransforms> cameraTransforms, Function<ItemOverrideList, ItemOverrideList> itemOverrideList){
		this(Optional.ofNullable(ambientOcclusion), Optional.ofNullable(gui3d), Optional.ofNullable(texture), Optional.ofNullable(cameraTransforms), Optional.ofNullable(itemOverrideList));
	}

	@Override
	public UnpackedSimpleBakedModel pipe(UnpackedSimpleBakedModel in){
		ambientOcclusion.ifPresent(funct -> in.setAmbientOcclusion(funct.apply(in.isAmbientOcclusion())));
		gui3d.ifPresent(funct -> in.setGui3d(funct.apply(in.isGui3d())));
		texture.ifPresent(funct -> in.setTexture(funct.apply(in.getTexture())));
		cameraTransforms.ifPresent(funct -> in.setCameraTransforms(funct.apply(in.getCameraTransforms())));
		itemOverrideList.ifPresent(funct -> in.setItemOverrideList(funct.apply(in.getItemOverrideList())));
		return in;
	}

}
