package plus.dragons.creeperfirework.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import plus.dragons.creeperfirework.fabric.misc.ConfigurationFabric;

public class CreeperFireworkFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(ConfigurationFabric.class, JanksonConfigSerializer::new);
    }
}
