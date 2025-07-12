package net.samuel.testdesertmod.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent FROG_LEG = new FoodComponent.Builder().snack().nutrition(2).saturationModifier(0.25f).build();
    public static final FoodComponent FROG_ON_STICK = new FoodComponent.Builder().nutrition(7).saturationModifier(0.9f).statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600, 0), 0.2F).build();
}
