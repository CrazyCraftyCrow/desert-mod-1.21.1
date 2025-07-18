package net.samuel.testdesertmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
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
        List<ItemConvertible> FROG_SMELTABLES = List.of(ModItems.FROG_LEG);

        offerSmelting(exporter, SAND_GOLD_SMELTABLES, RecipeCategory.MISC, Items.GOLD_INGOT, 0.25f, 200, "gold_ingot");
        offerBlasting(exporter, SAND_GOLD_SMELTABLES, RecipeCategory.MISC, Items.GOLD_INGOT, 0.25f, 100, "gold_ingot");

        offerSmelting(exporter, FROG_SMELTABLES, RecipeCategory.MISC, ModItems.FROG_ON_STICK, 0.25f, 100, "frog_on_stick");

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.REDSTONE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(ModItems.BROKEN_ANCIENT_AXE),
                        Ingredient.ofItems(Items.REDSTONE_BLOCK),
                        RecipeCategory.MISC,
                        ModItems.ANCIENT_AXE)
                .criterion(hasItem(ModItems.REDSTONE_UPGRADE_SMITHING_TEMPLATE),conditionsFromItem(ModItems.REDSTONE_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(exporter, getItemPath(ModItems.ANCIENT_AXE) + "_smithing");



        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BROKEN_ANCIENT_AXE)
                .pattern("T")
                .pattern("S")
                .pattern("B")
                .input('T', ModItems.ANCIENT_AXE_TOP)
                .input('S', Items.STICK)
                .input('B', ModItems.ANCIENT_AXE_BOTTOM)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

    }
}
