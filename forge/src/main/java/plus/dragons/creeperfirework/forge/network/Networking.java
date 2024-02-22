package plus.dragons.creeperfirework.forge.network;

import net.minecraft.util.Identifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Networking {
    public static SimpleChannel INSTANCE;
    public static Identifier ID = new Identifier("creeper_firework", "networking");

    @SubscribeEvent
    public static void registerNetworking(FMLCommonSetupEvent event) {
        event.enqueueWork(Networking::registerMessage);
    }

    public static void registerMessage() {
        INSTANCE = ChannelBuilder
                .named(ID)
                .networkProtocolVersion(0)
                .simpleChannel()
                .messageBuilder(Packet.class,NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Packet::new)
                .encoder(Packet::write)
                .consumerNetworkThread(Packet::handle)
                .add();
    }
}
