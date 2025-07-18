package net.samuel.testdesertmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;
import net.samuel.testdesertmod.block.ModBlocks;
import net.samuel.testdesertmod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.SAND_GOLD_ORE, oreDrops(ModBlocks.SAND_GOLD_ORE, ModItems.RAW_SAND_GOLD));
        addDrop(ModBlocks.QUICK_SAND, drops(ModBlocks.QUICK_SAND));

        this.addDrop(ModBlocks.DRIED_GRASS,
                block -> this.dropsWithShears(
                        block,
                        (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                                block, ItemEntry.builder(Items.WHEAT_SEEDS).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 1.0F)))
                        )
                )
        );

        this.addDrop(ModBlocks.SHORT_DRIED_GRASS,
                block -> this.dropsWithShears(
                        block,
                        (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                                block, ItemEntry.builder(Items.WHEAT_SEEDS).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.0F)))
                        )
                )
        );
    }
}
