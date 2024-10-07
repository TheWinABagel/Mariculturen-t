package dev.bagel.fishin.registry;

import dev.bagel.fishin.Fishin;
import dev.bagel.fishin.fish.rods.CustomFishingHook;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.FishingHook;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Fishin.MODID);
    public static final DeferredHolder<EntityType<? extends Entity>, EntityType<CustomFishingHook>> FISHING_BOBBER = ENTITIES.register("fishing_bobber", () ->
            EntityType.Builder.<CustomFishingHook>of(CustomFishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("fishing_bobber"));

    public static void init(IEventBus modBus) {
        ENTITIES.register(modBus);
    }
}
