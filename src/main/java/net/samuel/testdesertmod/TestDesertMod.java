package net.samuel.testdesertmod;

import net.fabricmc.api.ModInitializer;

import net.samuel.testdesertmod.block.ModBlocks;
import net.samuel.testdesertmod.item.ModItems;
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

		ModWorldGeneration.generateModWorldGen();
	}
}