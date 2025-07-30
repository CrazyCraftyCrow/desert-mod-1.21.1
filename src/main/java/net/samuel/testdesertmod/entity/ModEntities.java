package net.samuel.testdesertmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.entity.custom.DesertRainFrogEntity;
import net.samuel.testdesertmod.entity.custom.SandWeaverEntity;

public class ModEntities {

    public static  final EntityType<DesertRainFrogEntity> DESERT_RAIN_FROG = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(TestDesertMod.MOD_ID, "desert_rain_frog"),
            EntityType.Builder.create(DesertRainFrogEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.25f,0.25f).build());

    public static  final EntityType<SandWeaverEntity> SANDWEAVER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(TestDesertMod.MOD_ID, "sandweaver"),
            EntityType.Builder.create(SandWeaverEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.8f,0.8f).build());

    public static void registerModEntities() {
        TestDesertMod.LOGGER.info("Registering Mod Entities for " + TestDesertMod.MOD_ID);
    }
}
