package net.samuel.testdesertmod.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.entity.custom.SandWeaverEntity;

public class SandWeaverRenderer extends MobEntityRenderer<SandWeaverEntity, SandWeaverModel<SandWeaverEntity>> {
    public SandWeaverRenderer(EntityRendererFactory.Context context) {
        super(context, new SandWeaverModel<>(context.getPart(SandWeaverModel.SANDWEAVER)), 0.0f);
    }

    @Override
    public Identifier getTexture(SandWeaverEntity entity) {
        return Identifier.of(TestDesertMod.MOD_ID, "textures/entity/sandweaver/sandweaver.png");
    }

    @Override
    public void render(SandWeaverEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
