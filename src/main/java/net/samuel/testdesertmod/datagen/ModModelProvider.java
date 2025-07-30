package net.samuel.testdesertmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.block.ModBlocks;
import net.samuel.testdesertmod.item.ModItems;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SAND_GOLD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.QUICK_SAND);
        blockStateModelGenerator.registerTintableCross(ModBlocks.DRIED_GRASS, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.SHORT_DRIED_GRASS, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RAW_SAND_GOLD, Models.GENERATED);

        itemModelGenerator.register(ModItems.FROG_LEG, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROG_ON_STICK, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANCIENT_AXE_BOTTOM, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANCIENT_AXE_TOP, Models.GENERATED);
        itemModelGenerator.register(ModItems.BROKEN_ANCIENT_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.REDSTONE_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);

        itemModelGenerator.register(ModItems.ANCIENT_AXE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.DESERT_RAIN_FROG_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));

        itemModelGenerator.register(ModItems.SANDWEAVER_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));

    }
}
