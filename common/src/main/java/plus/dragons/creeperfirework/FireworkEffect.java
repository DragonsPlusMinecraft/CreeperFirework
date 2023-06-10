package plus.dragons.creeperfirework;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.mob.CreeperEntity;

public class FireworkEffect {
    @ExpectPlatform
    public static void create(CreeperEntity creeper){
        throw new RuntimeException();
    }
}
