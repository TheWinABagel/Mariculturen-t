package dev.bagel.fishin.fish.rods.abilities;

import dev.bagel.fishin.fish.ItemFish;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractAbility {
    private final ResourceLocation id;
    private final ResourceLocation castId;
    private boolean isCast = false;

    public AbstractAbility(ResourceLocation id) {
        this.id = id;
        this.castId = id.withSuffix("_cast");
    }

    /**
     * Fired when the bobber is cast
     * */
    public abstract InteractionResultHolder<ItemStack> onCast(Player player, Level level, ItemStack rodStack, InteractionHand hand);

    /**
     * Fired when a fish is successfully caught
     * */
    public abstract void onCatch(Player player, Level level, ItemStack rodStack, ItemFish caughtFish);

    /**
     * Fired when the rod is reeled in, with no fish caught
     * */
    public abstract void onReelIn(Player player, Level level, ItemStack rodStack);

    public Component getName() {
        return Component.translatable(id.toLanguageKey("ability"));
    }

    public Component getDesc() {
        return Component.translatable(id.toLanguageKey("ability", "info"));
    }
}
