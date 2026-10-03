package me.cocolennon.dreamworld.client.render;

import me.cocolennon.dreamworld.DreamWorld;
import me.cocolennon.dreamworld.client.model.PiwwoEntityModel;
import me.cocolennon.dreamworld.entities.PiwwoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class PiwwoEntityRenderer extends MobRenderer<PiwwoEntity, PiwwoRenderState, PiwwoEntityModel> {
    private static final Identifier TEXTURE = DreamWorld.id("textures/entity/piwwo.png");

    public PiwwoEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PiwwoEntityModel(context.bakeLayer(PiwwoEntityModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public PiwwoRenderState createRenderState() {
        return new PiwwoRenderState();
    }

    @Override
    public void extractRenderState(PiwwoEntity entity, PiwwoRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(PiwwoRenderState state) {
        return TEXTURE;
    }
}