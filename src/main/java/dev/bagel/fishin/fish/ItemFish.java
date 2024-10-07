package dev.bagel.fishin.fish;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemFish extends Item {
    public ItemFish(Properties properties) {
        super(properties.food(new FoodProperties.Builder().nutrition(10).saturationModifier(5).fast().build()));
    }
}
