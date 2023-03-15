package plus.dragons.creeperfirework;

import plus.dragons.creeperfirework.misc.Configuration;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class CreeperFirework implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(Configuration.class, JanksonConfigSerializer::new);
    }
}
