package plus.dragons.creeperfirework;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class Configuration {

    @ExpectPlatform
    public static boolean isCreeperExplodeIntoFirework(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static boolean isFireworkDestroyBlock(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static boolean isFireworkHurtCreature(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static double becomeFireworkChance(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static boolean isCreeperExplodeIntoFireworkWhenDie(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static boolean isDeathFireworkDestroyBlock(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static boolean isDeathFireworkHurtCreature(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static double becomeFireworkChanceWhenDie(){
        throw new RuntimeException();
    }
}
