package plus.dragons.creeperfirework.forge;

import plus.dragons.creeperfirework.forge.misc.ConfigurationForge;

public class ConfigurationImpl {
    public static boolean isCreeperExplodeIntoFirework() {
        return ConfigurationForge.CREEPER_EXPLODE_INTO_FIREWORK.get();
    }

    public static boolean isFireworkDestroyBlock() {
        return ConfigurationForge.CREEPER_FIREWORK_DESTROY_BLOCK.get();
    }

    public static boolean isFireworkHurtCreature() {
        return ConfigurationForge.CREEPER_FIREWORK_HURT_CREATURE.get();
    }

    public static double becomeFireworkChance() {
        return ConfigurationForge.CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY.get();
    }

    public static boolean isCreeperExplodeIntoFireworkWhenDie() {
        return ConfigurationForge.CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE.get();
    }

    public static boolean isDeathFireworkDestroyBlock() {
        return ConfigurationForge.CREEPER_DEATH_FIREWORK_DESTROY_BLOCK.get();
    }

    public static boolean isDeathFireworkHurtCreature() {
        return ConfigurationForge.CREEPER_DEATH_FIREWORK_HURT_CREATURE.get();
    }

    public static double becomeFireworkChanceWhenDie() {
        return ConfigurationForge.CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE.get();
    }
}
