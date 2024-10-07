package dev.bagel.fishin.registry;

import dev.bagel.fishin.Fishin;
import dev.bagel.fishin.registry.components.RodComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Fishin.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RodComponent>> ROD_COMPONENT = DATA_COMPONENTS.register("rod_component", () ->
            DataComponentType.<RodComponent>builder().persistent(RodComponent.CODEC).networkSynchronized(RodComponent.STREAM_CODEC).build());

    public static void init(IEventBus modBus) {
        DATA_COMPONENTS.register(modBus);
    }
}
