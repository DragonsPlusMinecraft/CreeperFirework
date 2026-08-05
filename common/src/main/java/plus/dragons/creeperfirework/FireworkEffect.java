package plus.dragons.creeperfirework;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.monster.Creeper;

public class FireworkEffect {
    @ExpectPlatform
    public static void create(Creeper creeper){
        throw new RuntimeException();
    }
}
