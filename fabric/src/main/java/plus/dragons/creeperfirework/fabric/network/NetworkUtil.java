package plus.dragons.creeperfirework.fabric.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import plus.dragons.creeperfirework.network.Payload;

import java.util.List;

public class NetworkUtil {
    public static void notifyClient(ServerLevel world, BlockPos pos, boolean powered) {
        List<ServerPlayer> watchers = world.getPlayers(player -> player.blockPosition().closerThan(pos, 192));
        for (ServerPlayer player : watchers) {
            ServerPlayNetworking.send(player, new Payload(pos,powered));
        }
    }
}
