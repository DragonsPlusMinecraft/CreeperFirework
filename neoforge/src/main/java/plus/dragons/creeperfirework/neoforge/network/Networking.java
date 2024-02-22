package plus.dragons.creeperfirework.neoforge.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Networking {

    @SubscribeEvent
    public static void registerNetworking(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar("creeper_firework").versioned("1.0");
        registrar.play(Payload.ID, Payload::new, handler -> handler
                .client(PayloadHandler.getInstance()::handleData));
    }
}
