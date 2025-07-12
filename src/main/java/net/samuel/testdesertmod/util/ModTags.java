package net.samuel.testdesertmod.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_REDSTONE_TOOL = createTag("needs_redstone_tool");
        public static final TagKey<Block> INCORRECT_FOR_REDSTONE_TOOL = createTag("incorrect_for_redstone_tool");

        public static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(TestDesertMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(TestDesertMod.MOD_ID, name));
        }
    }
}
