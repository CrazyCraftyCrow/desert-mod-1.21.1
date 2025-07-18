package net.samuel.testdesertmod.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.block.ModBlocks;

import java.util.List;

import static net.minecraft.world.gen.feature.ConfiguredFeatures.createRandomPatchFeatureConfig;

public class ModConfiguredFeatures {

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }

    public static final RegistryKey<ConfiguredFeature<?, ?>> SAND_GOLD_ORE_KEY = registerKey("sand_gold_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> QUICK_SAND_KEY = registerKey("quick_sand_key");

    public static final RegistryKey<ConfiguredFeature<?, ?>> DRIED_GRASS_KEY = registerKey("dried_grass_key");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest sandReplaceables = new BlockMatchRuleTest(Blocks.SAND);

        List<OreFeatureConfig.Target> overworldSandGoldOres =
                List.of(OreFeatureConfig.createTarget(sandReplaceables, ModBlocks.SAND_GOLD_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldQuickSand =
                List.of(OreFeatureConfig.createTarget(sandReplaceables, ModBlocks.QUICK_SAND.getDefaultState()));

        register(context,SAND_GOLD_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSandGoldOres, 3));
        register(context,QUICK_SAND_KEY, Feature.ORE, new OreFeatureConfig(overworldQuickSand, 20));

        //ConfiguredFeatures.register(
       //         context, DRIED_GRASS_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.DRIED_GRASS), 30)
        //);

        ConfiguredFeatures.register(context, DRIED_GRASS_KEY, Feature.RANDOM_PATCH,
                createRandomPatchFeatureConfig(
                        new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.SHORT_DRIED_GRASS.getDefaultState(), 1).add(ModBlocks.DRIED_GRASS.getDefaultState(), 3)), 32
                )
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(TestDesertMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
