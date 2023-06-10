package plus.dragons.creeperfirework.fabric.misc;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;


@Config(name = "creeper_firework")
public class ConfigurationFabric implements ConfigData {

    @Comment("Will creeper's active-explosion turn into firework?")
    public boolean CREEPER_EXPLODE_INTO_FIREWORK = true;
    @Comment("Will the active-explosion firework destroy nearby environment just like creeper normally exploding?")
    public boolean CREEPER_FIREWORK_DESTROY_BLOCK = false;
    @Comment("Will the active-explosion firework effect hurt nearby creature?")
    public boolean CREEPER_FIREWORK_HURT_CREATURE = false;
    @Comment("The probability of creeper turning into firework when actively explodes. It must be bigger than 0.0 and not exceed 1.0.")
    public double CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY = 1.0;
    @Comment("Will creeper explode into firework when die?")
    public boolean CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE = false;
    @Comment("Will the death-explosion firework destroy nearby environment just like creeper normally exploding?")
    public boolean CREEPER_DEATH_FIREWORK_DESTROY_BLOCK = false;
    @Comment("Will the death-explosion firework effect hurt nearby creature?")
    public boolean CREEPER_DEATH_FIREWORK_HURT_CREATURE = false;
    @Comment("The probability of creeper turning into firework when die. It must be bigger than 0.0 and not exceed 1.0.")
    public double CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE = 1.0;


    public static ConfigurationFabric getRealTimeConfig() {
        return AutoConfig.getConfigHolder(ConfigurationFabric.class).getConfig();
    }
}
