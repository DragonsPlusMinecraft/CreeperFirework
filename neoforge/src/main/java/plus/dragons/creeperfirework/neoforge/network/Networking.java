package plus.dragons.creeperfirework.neoforge.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import plus.dragons.creeperfirework.network.Payload;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Networking {

    @SubscribeEvent
    public static void registerNetworking(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.MAIN);
        registrar.playToClient(
                Payload.ID,
                Payload.PACKET_CODEC,
                new MainThreadPayloadHandler<>(ClientPayloadHandler::handleDataOnMain)
        );
    }
}
