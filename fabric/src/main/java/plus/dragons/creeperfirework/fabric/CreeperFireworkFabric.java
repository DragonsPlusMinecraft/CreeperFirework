package plus.dragons.creeperfirework.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import plus.dragons.creeperfirework.fabric.misc.ConfigurationFabric;
import plus.dragons.creeperfirework.network.Payload;

public class CreeperFireworkFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(ConfigurationFabric.class, JanksonConfigSerializer::new);
        PayloadTypeRegistry.playS2C().register(Payload.ID, Payload.PACKET_CODEC);
    }
}
