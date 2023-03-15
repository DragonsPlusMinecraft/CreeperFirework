package plus.dragons.creeperfirework.misc;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;


@Config(name = "creeper_firework")
public class Configuration implements ConfigData {

    @Comment("Will creeper's active-explosion turn into firework?")
    public boolean ACTIVE_EXPLOSION_TO_FIREWORK = true;
    @Comment("Will the active-explosion firework destroy nearby environment just like creeper normally exploding?")
    public boolean DESTROY_BLOCK = false;
    @Comment("Will the active-explosion firework effect hurt nearby creature?")
    public boolean HURT_CREATURE = false;
    @Comment("The probability of creeper turning into firework when actively explodes. It must be bigger than 0.0 and not exceed 1.0.")
    public double TURNING_PROBABILITY = 1.0;
    @Comment("Will creeper explode into firework when die?")
    public boolean DEATH_TO_FIREWORK = false;
    @Comment("Will the death-explosion firework destroy nearby environment just like creeper normally exploding?")
    public boolean DEATH_EXPLOSION_DESTROY_BLOCK = false;
    @Comment("Will the death-explosion firework effect hurt nearby creature?")
    public boolean DEATH_EXPLOSION_HURT_CREATURE = false;
    @Comment("The probability of creeper turning into firework when die. It must be bigger than 0.0 and not exceed 1.0.")
    public double DEATH_EXPLOSION_TURNING_PROBABILITY = 1.0;


    public static Configuration getRealTimeConfig() {
        return AutoConfig.getConfigHolder(Configuration.class).getConfig();
    }
}
