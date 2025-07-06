package net.samuel.testdesertmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.samuel.testdesertmod.block.ModBlocks;
import net.samuel.testdesertmod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        List<ItemConvertible> SAND_GOLD_SMELTABLES = List.of(ModItems.RAW_SAND_GOLD, ModBlocks.SAND_GOLD_ORE);

        offerSmelting(exporter, SAND_GOLD_SMELTABLES, RecipeCategory.MISC, Items.GOLD_INGOT, 0.25f, 200, "gold_ingot");
        offerBlasting(exporter, SAND_GOLD_SMELTABLES, RecipeCategory.MISC, Items.GOLD_INGOT, 0.25f, 100, "gold_ingot");

    }
}
