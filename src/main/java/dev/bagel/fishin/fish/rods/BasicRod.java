package dev.bagel.fishin.fish.rods;

import dev.bagel.fishin.fish.rods.abilities.FishingAbilities;

public class BasicRod extends AbstractRod {
    public BasicRod(Properties properties, int durability) {
        super(properties, durability, 9);
        abilities.set(0, FishingAbilities.HOOK_ABILITY);
        abilities.set(1, FishingAbilities.HOOK_ABILITY);
        abilities.set(5, FishingAbilities.HOOK_ABILITY);
    }
}
