package net.samuel.testdesertmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;

public class ModItems {

    public static final Item RAW_SAND_GOLD = registerItem("raw_sand_gold", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TestDesertMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TestDesertMod.LOGGER.info("Registering Mod Items for " + TestDesertMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RAW_SAND_GOLD);
        });
    }
}
