package plus.dragons.creeperfirework.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import plus.dragons.creeperfirework.neoforge.misc.ConfigurationNeoForge;

@Mod("creeper_firework")
public class CreeperFireworkNeoForge {

    public CreeperFireworkNeoForge(IEventBus eventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationNeoForge.MOD_CONFIG);
    }

}
