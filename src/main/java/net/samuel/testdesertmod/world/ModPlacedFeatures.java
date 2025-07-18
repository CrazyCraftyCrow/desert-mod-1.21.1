package net.samuel.testdesertmod.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

import net.samuel.testdesertmod.TestDesertMod;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> SAND_GOLD_ORE_PLACED_KEY = registerKey("sand_gold_ore_placed");
    public static final RegistryKey<PlacedFeature> QUICK_SAND_PLACED_KEY = registerKey("quick_sand_placed");

    public static final RegistryKey<PlacedFeature> DRIED_GRASS_PLACED_KEY = registerKey("dried_grass_placed_key");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, SAND_GOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SAND_GOLD_ORE_KEY),
                ModOrePlacement.modifiersWithCount(15,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(90))));

        register(context, QUICK_SAND_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUICK_SAND_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(90))));

        register(context, DRIED_GRASS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DRIED_GRASS_KEY),
                RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(TestDesertMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}

