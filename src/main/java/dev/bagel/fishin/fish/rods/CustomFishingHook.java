package dev.bagel.fishin.fish.rods;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CustomFishingHook extends FishingHook {
    public CustomFishingHook(EntityType<? extends FishingHook> entityType, Level level) {
        super(entityType, level);
    }

    public CustomFishingHook(Player player, Level level, int luck, int lureSpeed) {
        super(player, level, luck, lureSpeed);
        player.sendSystemMessage(Component.literal("woah hi this is a custom fishing hook").withStyle(ChatFormatting.AQUA));
        player.displayClientMessage(Component.literal("woah hi this is a custom fishing hook").withStyle(ChatFormatting.RED), false);
    }

    //Amount the rod is damaged when catching a fish
    @Override
    public int retrieve(ItemStack stack) {

        return super.retrieve(stack);
    }
}
