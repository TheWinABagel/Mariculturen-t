package dev.bagel.fishin.fish.rods;

import dev.bagel.fishin.fish.rods.abilities.AbstractAbility;
import dev.bagel.fishin.fish.rods.abilities.FishingAbilities;
import dev.bagel.fishin.registry.ModComponents;
import dev.bagel.fishin.registry.components.RodComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractRod extends FishingRodItem {
    protected final NonNullList<AbstractAbility> abilities;
    public AbstractRod(Properties properties, int durability, int maxSlots) {
        super(properties.stacksTo(1).component(ModComponents.ROD_COMPONENT, new RodComponent(0, maxSlots)).durability(durability));
        abilities = NonNullList.withSize(maxSlots, FishingAbilities.EMPTY_ABILITY);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack rodStack = player.getItemInHand(hand);
        RodComponent component = rodStack.get(ModComponents.ROD_COMPONENT);
        if (component != null) {
            player.displayClientMessage(Component.literal("selected: %d, world is %s".formatted(component.selected(), level)), false);
            return abilities.get(component.selected()).onCast(player, level, rodStack, hand);
        }
        else {
            player.displayClientMessage(Component.literal("Rod component is null, how??"), false);
            return InteractionResultHolder.fail(rodStack);
        }
    }
}
