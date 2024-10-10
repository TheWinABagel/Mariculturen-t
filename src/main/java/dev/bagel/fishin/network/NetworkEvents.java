package dev.bagel.fishin.network;

import dev.bagel.fishin.network.payload.C2SUpdateComponent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkEvents {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        /*registrar.playToClient(
                S2CXPDataUpdateReply.TYPE,
                S2CXPDataUpdateReply.STREAM_CODEC,
                new S2CXPDataUpdateReply.Handler());*/
        registrar.playToServer(
                C2SUpdateComponent.TYPE,
                C2SUpdateComponent.STREAM_CODEC,
                new C2SUpdateComponent.Handler());
    }
}
