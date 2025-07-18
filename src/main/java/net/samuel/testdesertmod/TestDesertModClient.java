package net.samuel.testdesertmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.samuel.testdesertmod.block.ModBlocks;
import net.samuel.testdesertmod.entity.ModEntities;
import net.samuel.testdesertmod.entity.client.DesertRainFrogModel;
import net.samuel.testdesertmod.entity.client.DesertRainFrogRenderer;

public class TestDesertModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(DesertRainFrogModel.DESERT_RAIN_FROG, DesertRainFrogModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.DESERT_RAIN_FROG, DesertRainFrogRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIED_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHORT_DRIED_GRASS, RenderLayer.getCutout());
    }
}
