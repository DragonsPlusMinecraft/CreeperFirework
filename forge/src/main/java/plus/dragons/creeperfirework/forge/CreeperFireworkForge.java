package plus.dragons.creeperfirework.forge;

import plus.dragons.creeperfirework.forge.misc.ConfigurationForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("creeper_firework")
public class CreeperFireworkForge {

    public CreeperFireworkForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationForge.MOD_CONFIG);
    }

}
