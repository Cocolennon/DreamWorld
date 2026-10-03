package me.cocolennon.dreamworld;

import me.cocolennon.dreamworld.client.model.PiwwoEntityModel;
import me.cocolennon.dreamworld.client.render.PiwwoEntityRenderer;
import me.cocolennon.dreamworld.entities.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class DreamWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLayerRegistry.registerModelLayer(PiwwoEntityModel.LAYER_LOCATION, PiwwoEntityModel::createBodyLayer);
        EntityRenderers.register(ModEntityTypes.PIWWO, PiwwoEntityRenderer::new);
    }
}
