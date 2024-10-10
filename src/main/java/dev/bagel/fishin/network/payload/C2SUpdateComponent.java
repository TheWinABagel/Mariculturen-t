package dev.bagel.fishin.network.payload;

import dev.bagel.fishin.Fishin;
import dev.bagel.fishin.registry.ModComponents;
import dev.bagel.fishin.registry.components.RodComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record C2SUpdateComponent(RodComponent component, ItemStack stack) implements CustomPacketPayload {
    public static final Type<C2SUpdateComponent> TYPE = new Type<>(Fishin.loc("update_comp"));
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SUpdateComponent> STREAM_CODEC = StreamCodec.composite(
            RodComponent.STREAM_CODEC,
            C2SUpdateComponent::component,
            ItemStack.STREAM_CODEC,
            C2SUpdateComponent::stack,
            C2SUpdateComponent::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final class Handler implements IPayloadHandler<C2SUpdateComponent> {
        @Override
        public void handle(C2SUpdateComponent payload, IPayloadContext context) {
            context.player().sendSystemMessage(Component.literal("server received message! with component %s".formatted(payload.component())));
            payload.stack().set(ModComponents.ROD_COMPONENT, payload.component());
        }
    }
}
