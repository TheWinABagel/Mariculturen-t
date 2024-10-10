package dev.bagel.fishin.network.payload;

import dev.bagel.fishin.Fishin;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record S2CXPDataUpdateReply(int id) implements CustomPacketPayload {
    public static final Type<S2CXPDataUpdateReply> TYPE = new Type<>(Fishin.loc("request_reply"));
    public static final StreamCodec<ByteBuf, S2CXPDataUpdateReply> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            S2CXPDataUpdateReply::id,
//            NetData.STREAM_CODEC,
//            S2CXPDataUpdateReply::data,
            S2CXPDataUpdateReply::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final class Handler implements IPayloadHandler<S2CXPDataUpdateReply> {
        @Override
        public void handle(S2CXPDataUpdateReply payload, IPayloadContext context) {

        }
    }
}
