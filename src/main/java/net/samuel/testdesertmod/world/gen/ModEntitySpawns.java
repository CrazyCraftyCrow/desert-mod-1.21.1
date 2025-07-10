package net.samuel.testdesertmod.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.samuel.testdesertmod.entity.ModEntities;
import net.samuel.testdesertmod.entity.custom.DesertRainFrogEntity;

public class ModEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                SpawnGroup.CREATURE, ModEntities.DESERT_RAIN_FROG, 30,1,2);

        SpawnRestriction.register(ModEntities.DESERT_RAIN_FROG, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DesertRainFrogEntity::canSpawn);
    }
}
