package plus.dragons.creeperfirework.neoforge;

import plus.dragons.creeperfirework.neoforge.misc.ConfigurationNeoForge;

public class ConfigurationImpl {
    public static boolean isCreeperExplodeIntoFirework() {
        return ConfigurationNeoForge.CREEPER_EXPLODE_INTO_FIREWORK.get();
    }

    public static boolean isFireworkDestroyBlock() {
        return ConfigurationNeoForge.CREEPER_FIREWORK_DESTROY_BLOCK.get();
    }

    public static boolean isFireworkHurtCreature() {
        return ConfigurationNeoForge.CREEPER_FIREWORK_HURT_CREATURE.get();
    }

    public static double becomeFireworkChance() {
        return ConfigurationNeoForge.CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY.get();
    }

    public static boolean isCreeperExplodeIntoFireworkWhenDie() {
        return ConfigurationNeoForge.CREEPER_EXPLODE_INTO_FIREWORK_WHEN_DIE.get();
    }

    public static boolean isDeathFireworkDestroyBlock() {
        return ConfigurationNeoForge.CREEPER_DEATH_FIREWORK_DESTROY_BLOCK.get();
    }

    public static boolean isDeathFireworkHurtCreature() {
        return ConfigurationNeoForge.CREEPER_DEATH_FIREWORK_HURT_CREATURE.get();
    }

    public static double becomeFireworkChanceWhenDie() {
        return ConfigurationNeoForge.CREEPER_EXPLODE_INTO_FIREWORK_PROBABILITY_WHEN_DIE.get();
    }
}
