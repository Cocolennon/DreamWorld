package me.cocolennon.dreamworld.client.model; // adjust

import me.cocolennon.dreamworld.DreamWorld;
import me.cocolennon.dreamworld.client.render.PiwwoRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class PiwwoEntityModel extends EntityModel<PiwwoRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(DreamWorld.id("piwwo"), "main");

    private final KeyframeAnimation walkAnim;
    private final KeyframeAnimation flopAnim;

    public PiwwoEntityModel(ModelPart root) {
        super(root);
        this.walkAnim = PiwwoEntityAnimations.WALK.bake(root);
        this.flopAnim = PiwwoEntityAnimations.FLOP.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("blnub", CubeListBuilder.create().texOffs(8, 18).addBox(3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("brnub", CubeListBuilder.create().texOffs(0, 18).addBox(-6.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("tlnub", CubeListBuilder.create().texOffs(0, 22).addBox(3.0F, -18.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("trnub", CubeListBuilder.create().texOffs(16, 18).addBox(-6.0F, -18.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -2.0F, 9.0F, 14.0F, 4.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(PiwwoRenderState state) {
        super.setupAnim(state);
        this.walkAnim.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2.0F, 2.5F);
        this.flopAnim.apply(state.flopAnimationState, state.ageInTicks);
    }
}