package dev.bagel.fishin.registry;

import dev.bagel.fishin.Fishin;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> FISHING_RODS = item("rods");

    private static TagKey<Item> item(String name) {
        return TagKey.create(Registries.ITEM, Fishin.loc(name));
    }
}
