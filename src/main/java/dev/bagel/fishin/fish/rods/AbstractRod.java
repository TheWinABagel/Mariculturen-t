package dev.bagel.fishin.fish.rods;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class AbstractRod extends FishingRodItem {
    public AbstractRod(Properties properties, int durability) {
        super(properties.stacksTo(1).durability(durability));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack currentItem = player.getItemInHand(hand);

        if (player.fishing != null) {
            if (!world.isClientSide) {
                int damageAmount = player.fishing.retrieve(currentItem);
                ItemStack originalItem = currentItem.copy();
                currentItem.hurtAndBreak(damageAmount, player, LivingEntity.getSlotForHand(hand));

                if (currentItem.isEmpty()) {
                    net.neoforged.neoforge.event.EventHooks.onPlayerDestroyItem(player, originalItem, hand);
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL,
                    1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL,
                    0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

            if (world instanceof ServerLevel serverLevel) {
                int fishingTimeReduction = (int) (EnchantmentHelper.getFishingTimeReduction(serverLevel, currentItem, player) * 20.0F);
                int fishingLuckBonus = EnchantmentHelper.getFishingLuckBonus(serverLevel, currentItem, player);
                world.addFreshEntity(new CustomFishingHook(player, world, fishingLuckBonus, fishingTimeReduction));
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(currentItem, world.isClientSide());
    }
}
