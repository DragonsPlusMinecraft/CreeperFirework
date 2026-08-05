package plus.dragons.creeperfirework.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Creeper;
import plus.dragons.creeperfirework.fabric.network.NetworkUtil;

public class FireworkEffectImpl {
    public static void create(Creeper creeper) {
        NetworkUtil.notifyClient((ServerLevel) creeper.level(), creeper.blockPosition(), creeper.isPowered());
    }
}
