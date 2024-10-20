package plus.dragons.creeperfirework.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import plus.dragons.creeperfirework.neoforge.misc.ConfigurationNeoForge;

@Mod("creeper_firework")
public class CreeperFireworkNeoForge {

    public CreeperFireworkNeoForge(ModContainer container) {
       container.registerConfig(ModConfig.Type.COMMON, ConfigurationNeoForge.MOD_CONFIG);
    }

}
