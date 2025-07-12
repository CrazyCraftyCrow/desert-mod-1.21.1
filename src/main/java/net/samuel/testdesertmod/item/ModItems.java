package net.samuel.testdesertmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.entity.ModEntities;

public class ModItems {

    public static final Item RAW_SAND_GOLD = registerItem("raw_sand_gold", new Item(new Item.Settings()));

    public static final Item ANCIENT_AXE_BOTTOM = registerItem("ancient_axe_bottom", new Item(new Item.Settings()));
    public static final Item ANCIENT_AXE_TOP = registerItem("ancient_axe_top", new Item(new Item.Settings()));
    public static final Item BROKEN_ANCIENT_AXE = registerItem("broken_ancient_axe", new Item(new Item.Settings()));
    public static final Item REDSTONE_UPGRADE_SMITHING_TEMPLATE = (registerItem("redstone_upgrade_smithing_template", new Item(new Item.Settings())));

    public static final Item FROG_LEG = registerItem("frog_leg", new Item(new Item.Settings().food(ModFoodComponents.FROG_LEG)));
    public static final Item FROG_ON_STICK = registerItem("frog_on_stick", new Item(new Item.Settings().food(ModFoodComponents.FROG_ON_STICK)));

    public static final Item DESERT_RAIN_FROG_EGG = registerItem("desert_rain_frog_spawn_egg",
            new SpawnEggItem(ModEntities.DESERT_RAIN_FROG, 0x9dc783, 0xbfaf5f, new Item.Settings()));

    public static final Item ANCIENT_AXE = registerItem("ancient_axe",
            new AxeItem(ModToolMaterials.REDSTONE, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.REDSTONE, 5.0F, -3.0F))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TestDesertMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TestDesertMod.LOGGER.info("Registering Mod Items for " + TestDesertMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RAW_SAND_GOLD);
            entries.add(FROG_LEG);
            entries.add(FROG_ON_STICK);
            entries.add(ANCIENT_AXE_BOTTOM);
            entries.add(ANCIENT_AXE_TOP);
            entries.add(BROKEN_ANCIENT_AXE);
            entries.add(REDSTONE_UPGRADE_SMITHING_TEMPLATE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(ANCIENT_AXE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(DESERT_RAIN_FROG_EGG);
        });
    }
}
