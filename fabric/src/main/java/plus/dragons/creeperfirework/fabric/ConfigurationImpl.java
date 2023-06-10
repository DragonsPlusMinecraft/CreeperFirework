package plus.dragons.creeperfirework.fabric;

import plus.dragons.creeperfirework.fabric.misc.ConfigurationFabric;

public class ConfigurationImpl {
    public static boolean isCreeperExplodeIntoFirework() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_EXPLODE_INTO_FIREWORK;
    }

    public static boolean isFireworkDestroyBlock() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_FIREWORK_DESTROY_BLOCK;
    }

    public static boolean isFireworkHurtCreature() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_FIREWORK_HURT_CREATURE;
    }

    public static double becomeFireworkChance() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY;
    }

    public static boolean isCreeperExplodeIntoFireworkWhenDie() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE;
    }

    public static boolean isDeathFireworkDestroyBlock() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_DEATH_FIREWORK_DESTROY_BLOCK;
    }

    public static boolean isDeathFireworkHurtCreature() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_DEATH_FIREWORK_HURT_CREATURE;
    }

    public static double becomeFireworkChanceWhenDie() {
        return ConfigurationFabric.getRealTimeConfig().CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE;
    }
}
