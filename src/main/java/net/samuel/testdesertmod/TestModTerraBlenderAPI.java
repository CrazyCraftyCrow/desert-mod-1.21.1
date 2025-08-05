package net.samuel.testdesertmod;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.samuel.testdesertmod.world.biome.surface.ModMaterialRules;
import terrablender.api.*;
import terrablender.api.ParameterUtils.Continentalness;
import terrablender.api.ParameterUtils.Depth;
import terrablender.api.ParameterUtils.Erosion;
import terrablender.api.ParameterUtils.Humidity;
import terrablender.api.ParameterUtils.ParameterPointListBuilder;
import terrablender.api.ParameterUtils.Temperature;
import terrablender.api.ParameterUtils.Weirdness;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class TestModTerraBlenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized()
    {
        Regions.register(new MapleForestRegion(Identifier.of(TestDesertMod.MOD_ID, "maple_forest"), 2));
    }

    public class MapleForestRegion extends Region
    {
        public MapleForestRegion(Identifier id, int weight)
        {
            super(id, RegionType.OVERWORLD, weight);
        }

        @Override
        public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
            VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

            new ParameterPointListBuilder()
                    .temperature(Temperature.span(Temperature.HOT, Temperature.HOT))
                    .humidity(Humidity.span(Humidity.DRY, Humidity.DRY))
                    .continentalness(Continentalness.NEAR_INLAND)
                    .erosion(Erosion.FULL_RANGE)
                    .depth(Depth.SURFACE, Depth.FLOOR)
                    .weirdness(Weirdness.FULL_RANGE)
                    .build().forEach(point -> builder.add(point, TestDesertMod.MAPLE_BIOME_KEY));

            builder.build().forEach(mapper);

            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, TestDesertMod.MOD_ID, ModMaterialRules.makeRules());
        }
    }
}
