package net.samuel.testdesertmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.samuel.testdesertmod.entity.ModEntities;
import net.samuel.testdesertmod.entity.client.DesertRainFrogModel;
import net.samuel.testdesertmod.entity.client.DesertRainFrogRenderer;

public class TestDesertModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DesertRainFrogModel.DESERT_RAIN_FROG, DesertRainFrogModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.DESERT_RAIN_FROG, DesertRainFrogRenderer::new);

    }
}
