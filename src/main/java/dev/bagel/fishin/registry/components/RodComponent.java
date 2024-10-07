package dev.bagel.fishin.registry.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RodComponent(int selected, int max) {
    public static final RodComponent DEFAULT = new RodComponent(0, 9);
    public static final Codec<RodComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("selected").forGetter(RodComponent::selected),
            Codec.INT.fieldOf("max").forGetter(RodComponent::max))
            .apply(inst, RodComponent::new));

    public static final StreamCodec<ByteBuf, RodComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            RodComponent::selected,
            ByteBufCodecs.VAR_INT,
            RodComponent::max,
            RodComponent::new
    );

    public RodComponent add(int amount) {
        return new RodComponent(addTest(amount), max);
    }

    private int addTest(int amount) {
        int newAmount = amount + selected;
        if (newAmount >= max) {
            return newAmount - max;
        } else if (newAmount < 0) {
            return newAmount + max;
        } else {
            return newAmount;
        }
    }
}
