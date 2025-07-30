package net.samuel.testdesertmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.samuel.testdesertmod.block.ModBlocks;
import net.samuel.testdesertmod.entity.ModEntities;
import net.samuel.testdesertmod.entity.custom.DesertRainFrogEntity;
import net.samuel.testdesertmod.entity.custom.SandWeaverEntity;
import net.samuel.testdesertmod.item.ModItems;
import net.samuel.testdesertmod.util.ModLootTableModifiers;
import net.samuel.testdesertmod.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDesertMod implements ModInitializer {
	public static final String MOD_ID = "testdesertmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModLootTableModifiers.modifyLootTables();

		ModWorldGeneration.generateModWorldGen();

		ModEntities.registerModEntities();

		FabricDefaultAttributeRegistry.register(ModEntities.DESERT_RAIN_FROG, DesertRainFrogEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SANDWEAVER, SandWeaverEntity.createAttributes());
	}
}