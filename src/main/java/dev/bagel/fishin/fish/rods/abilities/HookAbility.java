package dev.bagel.fishin.fish.rods.abilities;

import dev.bagel.fishin.Fishin;
import dev.bagel.fishin.fish.ItemFish;
import dev.bagel.fishin.fish.rods.CustomFishingHook;
import dev.bagel.fishin.registry.ModComponents;
import dev.bagel.fishin.registry.components.RodComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class HookAbility extends AbstractAbility {
    HookAbility() {
        super(Fishin.loc("hook"));
    }

    @Override
    public InteractionResultHolder<ItemStack> onCast(Player player, Level world, ItemStack rodStack, InteractionHand hand) {
        //if player is already fishing
        if (player.fishing != null) {
            if (!world.isClientSide) {
                int damageAmount = player.fishing.retrieve(rodStack);
                ItemStack originalItem = rodStack.copy();
                rodStack.hurtAndBreak(damageAmount, player, LivingEntity.getSlotForHand(hand));

                if (rodStack.isEmpty()) {
                    net.neoforged.neoforge.event.EventHooks.onPlayerDestroyItem(player, originalItem, hand);
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL,
                    1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
            //player is not currently fishing
        } else {

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL,
                    0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

            if (world instanceof ServerLevel serverLevel) {
                int fishingTimeReduction = (int) (EnchantmentHelper.getFishingTimeReduction(serverLevel, rodStack, player) * 20.0F);
                int fishingLuckBonus = EnchantmentHelper.getFishingLuckBonus(serverLevel, rodStack, player);
                world.addFreshEntity(new CustomFishingHook(player, world, fishingLuckBonus, fishingTimeReduction));
            }

            player.awardStat(Stats.ITEM_USED.get(rodStack.getItem()));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(rodStack, world.isClientSide());
    }

    @Override
    public void onCatch(Player player, Level level, ItemStack rodStack, ItemFish caughtFish) {

    }

    @Override
    public void onReelIn(Player player, Level level, ItemStack rodStack) {

    }
}
