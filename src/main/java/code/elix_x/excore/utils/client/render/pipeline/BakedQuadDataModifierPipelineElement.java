package code.elix_x.excore.utils.client.render.pipeline;

import java.util.Optional;
import java.util.function.Function;

import code.elix_x.excomms.pipeline.PipelineElement;
import code.elix_x.excore.utils.client.render.model.UnpackedBakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BakedQuadDataModifierPipelineElement implements PipelineElement<UnpackedBakedQuad, UnpackedBakedQuad> {

	public static BakedQuadDataModifierPipelineElement tintIndex(Function<Integer, Integer> tintIndex){
		return new BakedQuadDataModifierPipelineElement(tintIndex, null, null, null);
	}

	public static BakedQuadDataModifierPipelineElement face(Function<EnumFacing, EnumFacing> face){
		return new BakedQuadDataModifierPipelineElement(null, face, null, null);
	}

	public static BakedQuadDataModifierPipelineElement sprite(Function<TextureAtlasSprite, TextureAtlasSprite> sprite){
		return new BakedQuadDataModifierPipelineElement(null, null, sprite, null);
	}

	public static BakedQuadDataModifierPipelineElement applyDiffuseLighting(Function<Boolean, Boolean> applyDiffuseLighting){
		return new BakedQuadDataModifierPipelineElement(null, null, null, applyDiffuseLighting);
	}

	private final Optional<Function<Integer, Integer>> tintIndex;
	private final Optional<Function<EnumFacing, EnumFacing>> face;
	private final Optional<Function<TextureAtlasSprite, TextureAtlasSprite>> sprite;
	private final Optional<Function<Boolean, Boolean>> applyDiffuseLighting;

	public BakedQuadDataModifierPipelineElement(Optional<Function<Integer, Integer>> tintIndex, Optional<Function<EnumFacing, EnumFacing>> face, Optional<Function<TextureAtlasSprite, TextureAtlasSprite>> sprite, Optional<Function<Boolean, Boolean>> applyDiffuseLighting){
		this.tintIndex = tintIndex;
		this.face = face;
		this.sprite = sprite;
		this.applyDiffuseLighting = applyDiffuseLighting;
	}

	public BakedQuadDataModifierPipelineElement(Function<Integer, Integer> tintIndex, Function<EnumFacing, EnumFacing> face, Function<TextureAtlasSprite, TextureAtlasSprite> sprite, Function<Boolean, Boolean> applyDiffuseLighting){
		this(Optional.ofNullable(tintIndex), Optional.ofNullable(face), Optional.ofNullable(sprite), Optional.ofNullable(applyDiffuseLighting));
	}

	@Override
	public UnpackedBakedQuad pipe(UnpackedBakedQuad in){
		tintIndex.ifPresent(funct -> in.setTintIndex(funct.apply(in.getTintIndex())));
		face.ifPresent(funct -> in.setFace(funct.apply(in.getFace())));
		sprite.ifPresent(funct -> in.setSprite(funct.apply(in.getSprite())));
		applyDiffuseLighting.ifPresent(funct -> in.setApplyDiffuseLighting(funct.apply(in.isApplyDiffuseLighting())));
		return in;
	}

}
