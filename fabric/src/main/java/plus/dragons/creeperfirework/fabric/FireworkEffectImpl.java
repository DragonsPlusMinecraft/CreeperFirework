package plus.dragons.creeperfirework.fabric;

import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import plus.dragons.creeperfirework.fabric.network.NetworkUtil;
import plus.dragons.creeperfirework.mixin.CreeperEntityAccessor;

public class FireworkEffectImpl {
    public static void create(CreeperEntity creeper) {
        NetworkUtil.notifyClient((ServerWorld) creeper.getWorld(),creeper.getBlockPos(),creeper.getDataTracker().get(CreeperEntityAccessor.getChargedTrackedDataKey()));
    }
}
