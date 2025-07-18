package net.samuel.testdesertmod.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;

import static net.minecraft.block.Blocks.register;

public class ModBlocks {

    public static final Block SAND_GOLD_ORE = registerBlock("sand_gold_ore",
            new FallingBlock(AbstractBlock.Settings.create().strength(2f)
                    .requiresTool().sounds(BlockSoundGroup.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block QUICK_SAND = registerBlock("quick_sand",
            new QuickSandBlock(
                    AbstractBlock.Settings.create().strength(0.5f).requiresTool().velocityMultiplier(0.4F).jumpVelocityMultiplier(0.5F).nonOpaque().sounds(BlockSoundGroup.SAND)
            )
    );

    public static final Block DRIED_GRASS = registerBlock("dried_grass",
            new DeadBushBlock(
                    AbstractBlock.Settings.create()
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block SHORT_DRIED_GRASS = registerBlock("short_dried_grass",
            new DeadBushBlock(
                    AbstractBlock.Settings.create()
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(TestDesertMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(TestDesertMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        TestDesertMod.LOGGER.info("Registering Mod Blocks for " + TestDesertMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.SAND_GOLD_ORE);
            entries.add(ModBlocks.DRIED_GRASS);
            entries.add(ModBlocks.SHORT_DRIED_GRASS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.QUICK_SAND);
        });
    }
}
