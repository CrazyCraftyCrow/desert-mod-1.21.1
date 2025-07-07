package net.samuel.testdesertmod.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.entity.custom.DesertRainFrogEntity;

public class DesertRainFrogRenderer extends MobEntityRenderer<DesertRainFrogEntity, DesertRainFrogModel<DesertRainFrogEntity>> {
    public DesertRainFrogRenderer(EntityRendererFactory.Context context) {
        super(context, new DesertRainFrogModel<>(context.getPart(DesertRainFrogModel.DESERT_RAIN_FROG)), 0.30f);
    }

    @Override
    public Identifier getTexture(DesertRainFrogEntity entity) {
        return Identifier.of(TestDesertMod.MOD_ID, "textures/entity/desert_rain_frog/desertrainfrog.png");
    }

    @Override
    public void render(DesertRainFrogEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
