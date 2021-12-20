package love.marblegate.creeperfirework.misc;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;


@Config(name = "creeper_firework")
public class Configuration implements ConfigData {
    @Comment("Will the firework destroy nearby environment just like creeper normally exploding?")
    public boolean DESTROY_BLOCK = false;
    @Comment("Will the firework effect hurt nearby creature?")
    public boolean HURT_CREATURE = false;
    @Comment("The probability of creeper turning into firework. It must be bigger than 0.0 and not exceed 1.0.")
    public double TURNING_PROBABILITY = 1.0;

    public static Configuration getRealTimeConfig() {
        return AutoConfig.getConfigHolder(Configuration.class).getConfig();
    }
}
