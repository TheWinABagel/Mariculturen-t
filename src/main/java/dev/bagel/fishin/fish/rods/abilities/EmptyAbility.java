package dev.bagel.fishin.fish.rods.abilities;

import dev.bagel.fishin.Fishin;
import dev.bagel.fishin.fish.ItemFish;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EmptyAbility extends AbstractAbility {

    EmptyAbility() {
        super(Fishin.loc("empty"));
    }

    @Override
    public InteractionResultHolder<ItemStack> onCast(Player player, Level level, ItemStack rodStack, InteractionHand hand) {
        player.displayClientMessage(Component.literal("Empty ability casted"), false);
        return InteractionResultHolder.pass(rodStack);
    }

    @Override
    public void onCatch(Player player, Level level, ItemStack rodStack, ItemFish caughtFish) {

    }

    @Override
    public void onReelIn(Player player, Level level, ItemStack rodStack) {

    }
}
