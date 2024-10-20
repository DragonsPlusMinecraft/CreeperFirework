package plus.dragons.creeperfirework.fabric.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import plus.dragons.creeperfirework.network.Payload;

import java.util.List;

public class NetworkUtil {
    public static void notifyClient(ServerWorld world, BlockPos pos, boolean powered) {
        List<ServerPlayerEntity> watchers = world.getPlayers(serverPlayerEntity -> serverPlayerEntity.getBlockPos().isWithinDistance(pos, 192));
        for (ServerPlayerEntity player : watchers) {
            ServerPlayNetworking.send(player, new Payload(pos,powered));
        }
    }
}
