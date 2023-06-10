package plus.dragons.creeperfirework.forge.misc;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationForge {

    public static final ForgeConfigSpec MOD_CONFIG;

    public static ForgeConfigSpec.BooleanValue CREEPER_EXPLODE_INTO_FIREWORK;
    public static ForgeConfigSpec.BooleanValue CREEPER_FIREWORK_DESTROY_BLOCK;
    public static ForgeConfigSpec.BooleanValue CREEPER_FIREWORK_HURT_CREATURE;
    public static ForgeConfigSpec.DoubleValue CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY;
    public static ForgeConfigSpec.BooleanValue CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE;
    public static ForgeConfigSpec.BooleanValue CREEPER_DEATH_FIREWORK_DESTROY_BLOCK;
    public static ForgeConfigSpec.BooleanValue CREEPER_DEATH_FIREWORK_HURT_CREATURE;
    public static ForgeConfigSpec.DoubleValue CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("explosion");
        CREEPER_EXPLODE_INTO_FIREWORK = builder.comment("Will creeper's active-explosion turn into firework?")
                .define("CREEPER_EXPLODE_INTO_FIREWORK", true);
        CREEPER_FIREWORK_HURT_CREATURE = builder.comment("Will the active-explosion firework destroy nearby environment just like creeper normally exploding?")
                .define("CREEPER_FIREWORK_HURT_CREATURE", false);
        CREEPER_FIREWORK_DESTROY_BLOCK = builder.comment("Will the active-explosion firework effect hurt nearby creature?")
                .define("CREEPER_FIREWORK_DESTROY_BLOCK", false);
        CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY = builder.comment("The probability of creeper turning into firework when actively explodes.")
                .defineInRange("CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY", 1.0, 0, 1.0);
        builder.pop();

        builder.push("death_explosion");
        CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE = builder.comment("Will creeper explode into firework when die?")
                .define("CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE", false);
        CREEPER_DEATH_FIREWORK_DESTROY_BLOCK = builder.comment("Will the death-explosion firework destroy nearby environment just like creeper normally exploding?")
                .define("CREEPER_DEATH_FIREWORK_DESTROY_BLOCK", false);
        CREEPER_DEATH_FIREWORK_HURT_CREATURE = builder.comment("Will the death-explosion firework effect hurt nearby creature?")
                .define("CREEPER_DEATH_FIREWORK_HURT_CREATURE", false);
        CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE = builder.comment("The probability of creeper turning into firework when die.")
                .defineInRange("CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE", 1.0, 0, 1.0);
        builder.pop();

        MOD_CONFIG = builder.build();
    }
}
